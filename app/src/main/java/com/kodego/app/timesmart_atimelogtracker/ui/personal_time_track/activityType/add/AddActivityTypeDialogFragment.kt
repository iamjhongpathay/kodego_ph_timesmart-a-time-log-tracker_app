package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.DialogFragmentAddActivityTypeBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityTypeViewModel
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*


class AddActivityTypeDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding : DialogFragmentAddActivityTypeBinding
    lateinit var activityTypeViewModel : ActivityTypeViewModel
    var defaultColor = 16764057

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogFragmentAddActivityTypeBinding.inflate(layoutInflater)
        activityTypeViewModel = ViewModelProvider(this)[ActivityTypeViewModel::class.java]

        //Exposed dropdown menu
        val activityTypeIconNames = resources.getStringArray(R.array.icon_names) //getting the list of icon names in string.xml
        activityTypeIconNames.sort() //ascending order
        val arrayAdapter = CustomDropdownAdapter(requireContext(), activityTypeIconNames) //CustomDropDownAdapter
        binding.spinnerAddActivityType.adapter = arrayAdapter

        //Button
        binding.btnSaveActivityType.setOnClickListener(){
            save()
            dismiss()
        }
        binding.btnCancelAddActivityType.setOnClickListener(){
            dismiss()
        }


        binding.btnChooseColor.setOnClickListener(){
            openColorPicker()
        }

        return binding.root
    }



    private fun openColorPicker() {
        var colorPicker = AmbilWarnaDialog(requireContext(), defaultColor, object :
            AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                defaultColor = color
                binding.btnChooseColor.setBackgroundColor(defaultColor)
            }

        })
        colorPicker.show()
    }

    private fun save(){
        var activityTypeName : String = binding.etAddActivityTypeName.text.toString()
        var icon : String = binding.spinnerAddActivityType.selectedItem.toString()
            .replaceFirstChar { it.lowercase(Locale.ROOT) }
        var iconColor : Int = defaultColor

        val activityType = ActivityType(0, activityTypeName, icon, iconColor)
        activityTypeViewModel.addActivityType(activityType)

        Toast.makeText(requireContext(), "New activity type saved!", Toast.LENGTH_SHORT).show()
    }
}