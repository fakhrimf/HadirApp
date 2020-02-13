package com.hadir.hadirapp

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.holder.ColorHolder
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.interfaces.ICrossfader
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.withEmail
import com.mikepenz.materialdrawer.model.interfaces.withIcon
import com.mikepenz.materialdrawer.model.interfaces.withIdentifier
import com.mikepenz.materialdrawer.model.interfaces.withName
import com.mikepenz.materialdrawer.util.getOptimalDrawerWidth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var headerView: AccountHeaderView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle


    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }



    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = getDrawable(R.drawable.ic_menu_white_24dp)
        //val profile = ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.profile)
//        val profile2 = ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.drawable.profile2)
//        val profile3 = ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(R.drawable.profile3)
//        val profile4 = ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(R.drawable.profile4)
//        val profile5 = ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(R.drawable.profile5)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, com.mikepenz.materialdrawer.R.string.material_drawer_open, com.mikepenz.materialdrawer.R.string.material_drawer_close)

        val profile = ProfileDrawerItem().apply {
            name = StringHolder("Muhamad Alfi")
            description = StringHolder("alfi@gmail.com")
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
        val item3= PrimaryDrawerItem()
        item3.run {
            identifier = 3
            name = StringHolder(R.string.text_profile)
            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
            icon = ImageHolder(R.drawable.ico_user_account_30px)
        }


        headerView = AccountHeaderView(this).apply {
            attachToSliderView(crossFadeLargeView)
            headerBackground = ImageHolder(R.drawable.header)
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
            onDrawerItemClickListener = { _, item, position ->
                println("Item Clicked $item $position ")
                false
            }

        }

        crossFadeSmallView.drawer = crossFadeLargeView
        drawer_layout.maxWidthPx = getOptimalDrawerWidth(this)
        crossFadeSmallView.background = crossFadeLargeView.background

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

//        val item1 = PrimaryDrawerItem()
//        item1.run {
//            identifier = 1
//            name = StringHolder(R.string.menu_home)
//            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
//        }
//        val item2 = SecondaryDrawerItem()
//        item2.run {
//            identifier = 2
//            name = StringHolder(R.string.menu_gallery)
//            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
//        }

//        itemAdapter.add(
//            item1,
//            item2
//        )
//        toolbar.setNavigationOnClickListener {
//            slider.drawerLayout?.openDrawer(slider)
//        }



//         Gada FindViewById, kecuali buat menu item
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)/
//        val navView: NavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

//    override fun onPostCreate(savedInstanceState: Bundle?) {
//        super.onPostCreate(savedInstanceState)
//        actionBarDrawerToggle.syncState()
//    }

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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
