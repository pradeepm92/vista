package com.health.vistacan.report

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.fragment.app.viewModels
import com.health.vistacan.report.ui.theme.PieChartYTTheme
import com.health.vistacan.report.ui.theme.*
import com.health.vistacan.report.viewmodel.ReportViewModel
import com.health.vistacan.ui.widgets.BarChart
import com.health.vistacan.ui.widgets.BarchartInput
import com.health.vistacan.ui.widgets.PieChart
import com.health.vistacan.ui.widgets.PieChartInput
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.mk.sheets.compose.samples.CalendarSample
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ReportFragment : Fragment() {

    private val viewModel: ReportViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val loading = viewModel.loading.value
                val scaffoldState = rememberScaffoldState()

                PieChartYTTheme (
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(white)
                            .padding(5.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Reports",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        if(recipes.isNotEmpty()){
                            Box(
                            modifier = Modifier
                                .fillMaxWidth().height(300.dp)
                        ) {
                                PieChart(
                                    modifier = Modifier
                                        .size(600.dp),
                                    input = listOf(
                                        PieChartInput(
                                            color = brightBlue,
                                            value = recipes[0],
                                            description = "null"
                                        ),
                                        PieChartInput(
                                            color = purple,
                                            value = recipes[1],
                                            description = "Insurance Claims >\$300"
                                        ),

                                        PieChartInput(
                                            color = redOrange,
                                            value = recipes[2],
                                            description = "Teleplan"
                                        ),
                                        PieChartInput(
                                            color = green,
                                            value = recipes[3],
                                            description = "Immediate On Treatment"
                                        ),
                                    ),
                                    centerText = "Health Reports"
                                )

                            }

                        }
                        if(recipes.isNotEmpty()) {
                            var showDescription by remember {
                            mutableStateOf(false)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(white)
                                .padding(30.dp),
                            contentAlignment = Alignment.TopCenter
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){

                                    BarChart(
                                        listOf(
                                            BarchartInput(recipes[4], "null", orange),
                                            BarchartInput(recipes[5], "Academy Hill Clinic", brightBlue),
                                            BarchartInput(recipes[6], "Whitefoot Clinic", green),
                                            BarchartInput(recipes[7], "Ski Patrole Big White", purple),
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        showDescription = showDescription
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            "Show description",
                                            color = gray,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Switch(
                                            checked = showDescription,
                                            onCheckedChange = {
                                                showDescription = it
                                            },
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = orange,
                                                uncheckedThumbColor = gray
                                            )
                                        )
                                    }
                                }}}
                    }
                }
            }
        }
    }
}
