package com.health.vistacan.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.health.vistacan.R
import com.health.vistacan.home.adapter.HomeAdapter
import com.health.vistacan.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), HomeAdapter.ItemClickListener{

    private var menuData = ArrayList<HomeModel>()
    private var homeBinding: FragmentHomeBinding? =null
    private lateinit var  navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        navController = NavHostFragment.findNavController(this)
        homeBinding!!.recyclerview.apply {
            layoutManager=GridLayoutManager(requireContext(),2)
            isNestedScrollingEnabled=false
            hasFixedSize()
            adapter=HomeAdapter(menuData ,this@HomeFragment)
        }

        return homeBinding!!.root
    }
    data class HomeModel(val id:Int,val name: String, val icon:Int)


    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null
    }

    private fun setData(){
        menuData.add(HomeModel(0,getString(R.string.newpatients),R.drawable.patient))
        menuData.add(HomeModel(1,getString(R.string.patients),R.drawable.patients))
        menuData.add(HomeModel(2,getString(R.string.encounters),R.drawable.encounters))
        menuData.add(HomeModel(3,getString(R.string.reports),R.drawable.reports))
        menuData.add(HomeModel(4,getString(R.string.settings),R.drawable.setting))
    }

    override fun itemClicked(id: Int) {
        Log.e("id", id.toString())
       when(id){
           0->{
               navController.navigate(R.id.action_homeFragment_to_patientFragment)
           }
           1->{
               navController.navigate(R.id.action_homeFragment_to_patientListFragment)
           }
           2->{
               navController.navigate(R.id.action_homeFragment_to_encounterListFragment)
           }
           3->{
               navController.navigate(R.id.reportFragment)
           }
           4->{
               navController.navigate(R.id.action_homeFragment_to_settingFragment)
           }
       }
    }


}