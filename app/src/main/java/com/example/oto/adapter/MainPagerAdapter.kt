package com.example.oto.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.oto.fragment.AktivitasFragment
import com.example.oto.fragment.KunciAplikasiFragment

class MainPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val pages = listOf(
        AktivitasFragment(),
        KunciAplikasiFragment()
    )

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> "Aktivitas"
            1-> "Kunci Aplikasi"
            else -> "Lainnya"
        }
    }
}