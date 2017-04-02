package com.nikita.movies_shmoovies

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.posters.PostersFragment

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_movies -> {
                showPostersFragment(PostersFragment.Type.Movies)
                true
            }
            R.id.navigation_tv_shows -> {
                showPostersFragment(PostersFragment.Type.TvShows)
                true
            }
            R.id.navigation_people -> {
                showPostersFragment(PostersFragment.Type.People)
                true
            }
            else -> false
        }
    }

    private val onCategoryItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.category_upcoming -> {
                setPostersFilter(PostersFragment.Filter.Upcoming)
                true
            }
            R.id.category_popular -> {
                setPostersFilter(PostersFragment.Filter.Popular)
                true
            }
            R.id.category_top_10 -> {
                setPostersFilter(PostersFragment.Filter.Top10)
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findView(R.id.toolbar))
        setTitle(R.string.app_name)
        val navigation = findView<BottomNavigationView>(R.id.navigation)
        val category = findView<BottomNavigationView>(R.id.navigation_category)
        // TODO Лень обходить баг с запоминанием выбранной вкладки
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        category.setOnNavigationItemSelectedListener(onCategoryItemSelectedListener)
        if (savedInstanceState == null) {
            showPostersFragment(PostersFragment.Type.Movies)
        }
    }


    private fun showPostersFragment(type: PostersFragment.Type) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, PostersFragment.create(type))
                .commit()
    }

    private fun setPostersFilter(filter: PostersFragment.Filter) {
        if (PostersFragment.filter != filter) {
            PostersFragment.filter = filter
            showPostersFragment(PostersFragment.type)
        }
    }

}
