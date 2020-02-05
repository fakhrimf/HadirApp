package com.hadir.hadirapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.hadir.hadirapp.R.drawable.ic_menu_gallery
import com.mikepenz.materialdrawer.holder.ColorHolder
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.MiniDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = getDrawable(R.drawable.ic_menu_camera)

        val item1 = PrimaryDrawerItem()
        item1.run {
            identifier = 1
            name = StringHolder(R.string.menu_home)
            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
        }
        val item2 = SecondaryDrawerItem()
        item2.run {
            identifier = 2
            name = StringHolder(R.string.menu_gallery)
            textColor = ColorHolder.fromColorRes(R.color.mainWhite)
        }

        slider.itemAdapter.add(
            item1,
            item2
         )
        toolbar.setNavigationOnClickListener {
            slider.drawerLayout?.openDrawer(slider)
        }
        slider.onDrawerItemClickListener={_, item, position ->
            println("Item Clicked $item $position ")
            false
        }



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


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
