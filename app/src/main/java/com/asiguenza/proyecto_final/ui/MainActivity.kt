package com.asiguenza.proyecto_final.ui



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiguenza.proyecto_final.R
import com.asiguenza.proyecto_final.databinding.ActivityLoginActiviyBinding


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HomeFragment())
            .commit()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.homeFragment -> fragment = HomeFragment()
                R.id.carritoFragment -> fragment = CarritoFragment()
                R.id.userFragment -> fragment = UserFragment()
            }
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                    .commit()
            }
            true
        }




    }

}





