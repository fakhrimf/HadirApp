package com.hadir.hadirapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadir.hadirapp.ui.home.HomeFragment
import com.hadir.hadirapp.ui.home.HomeViewModel
import com.hadir.hadirapp.ui.profile.ProfileFragment
import com.hadir.hadirapp.ui.statistics.StatisticsFragment
import com.hadir.hadirapp.utils.SharedPreferenceUtils
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.holder.ColorHolder
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.interfaces.ICrossfader
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.*
import com.mikepenz.materialdrawer.util.getOptimalDrawerWidth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var headerView: AccountHeaderView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val sharedPreferenceUtils: SharedPreferenceUtils = SharedPreferenceUtils.newInstance(this)


    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val username = sharedPreferenceUtils.getUsername();



        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, com.mikepenz.materialdrawer.R.string.material_drawer_open, com.mikepenz.materialdrawer.R.string.material_drawer_close)
        toolbar.navigationIcon = getDrawable(R.drawable.ic_menu_white_24dp)
        val listFragment = arrayListOf(
            HomeFragment.newInstance(),
            StatisticsFragment.newInstance(),
            ProfileFragment.newInstance()
            )

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment.newInstance())
            .commitNow()

        val profile = ProfileDrawerItem().apply {
            name = StringHolder("Muhamad Alfi")
            description = StringHolder(username)
            icon = ImageHolder(R.drawable.profile)
        }

        val item1 = PrimaryDrawerItem()
        item1.run {
            identifier = 1
            name = StringHolder(R.string.menu_home)
            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
            icon = ImageHolder(R.drawable.ico_home_30px)
        }

        val item2= PrimaryDrawerItem()
        item2.run {
            identifier = 2
            name = StringHolder(R.string.text_statistik)
            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
            icon = ImageHolder(R.drawable.ico_futures_30px)

        }
        val item3 = PrimaryDrawerItem()
        item3.run {
            identifier = 3
            name = StringHolder(R.string.text_profile)
            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
            icon = ImageHolder(R.drawable.ico_user_account_30px)
        }


        headerView = AccountHeaderView(this).apply {
            attachToSliderView(crossFadeLargeView)
            headerBackground = ImageHolder(R.drawable.background)
            dividerBelowHeader = false
            addProfiles(
                profile
            )
            withSavedInstance(savedInstanceState)
        }

        crossFadeLargeView.apply {
            itemAdapter.add(
                item1,item2,item3
            )
            addStickyDrawerItems(
                SecondaryDrawerItem().withName(R.string.drawer_logout).withIcon(R.drawable.drawer_logout).withIdentifier(4)
            )
            selectedItemPosition = 0
            onDrawerItemClickListener = { _, _, position->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, listFragment[position-1])
                    .commit()
               drawer_layout.closeDrawer(GravityCompat.START)
                false

            }

        }

        crossFadeSmallView.drawer = crossFadeLargeView
        drawer_layout.maxWidthPx = getOptimalDrawerWidth(this)

        crossFadeSmallView.crossFader = object : ICrossfader {

            override val isCrossfaded: Boolean
                get() = drawer_layout.isCrossfaded

            override fun crossfade() {
                val isFaded = isCrossfaded
                drawer_layout.crossfade(400)

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
            }
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(_outState: Bundle) {
        var outState = _outState
        //add the values which need to be saved from the drawer to the bundle
        outState = crossFadeLargeView.saveInstanceState(outState)

        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerView.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer_layout.isDrawerOpen(crossFadeSlider)) {
            drawer_layout.closeDrawer(crossFadeSlider)
        } else {
            super.onBackPressed()
        }
    }

}
