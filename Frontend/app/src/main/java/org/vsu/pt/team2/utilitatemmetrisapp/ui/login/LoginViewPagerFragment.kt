package org.vsu.pt.team2.utilitatemmetrisapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.vsu.pt.team2.utilitatemmetrisapp.R
import org.vsu.pt.team2.utilitatemmetrisapp.ui.adapters.DEMO_USER_PAGE_INDEX
import org.vsu.pt.team2.utilitatemmetrisapp.ui.adapters.LOGIN_PAGE_INDEX
import org.vsu.pt.team2.utilitatemmetrisapp.ui.adapters.LoginPagerAdapter
import org.vsu.pt.team2.utilitatemmetrisapp.databinding.FragmentLoginViewPagerBinding
import org.vsu.pt.team2.utilitatemmetrisapp.managers.IntentExtrasManager

class LoginViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = LoginPagerAdapter(this)


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.apply {
            attach()
        }
        activity?.intent?.let {
            if (IntentExtrasManager.continueRegister.getFrom(it))
                viewPager.currentItem = LOGIN_PAGE_INDEX
        }

        binding.appBarLayoutInclude.toolbarIconContainer.visibility = View.GONE

        (activity as AppCompatActivity).setSupportActionBar(binding.appBarLayoutInclude.toolbar)

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            DEMO_USER_PAGE_INDEX -> getString(R.string.auth_demo_tab_title)
            LOGIN_PAGE_INDEX -> getString(R.string.auth_login_tab_title)
            else -> null
        }
    }
}