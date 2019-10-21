package com.vetmypet.vetmypet.ui

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.vetmypet.vetmypet.R
import com.vetmypet.vetmypet.io.ApiService
import com.vetmypet.vetmypet.model.Specialty
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.card_view_step_one.*
import kotlinx.android.synthetic.main.card_view_step_two.*
import kotlinx.android.synthetic.main.card_view_step_three.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CreateActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()

    }

    private val selectedCalendar = Calendar.getInstance()
    private var selectedTimeRadioBtn: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btnNext.setOnClickListener {
            if (etDescription.text.toString().length < 3) {
                etDescription.error = getString(R.string.validate_mypet_description)
            } else {
                // continue to step 2
                cvStep1.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
        }

        btnNext2.setOnClickListener {
            when {
                etScheduledDate.text.toString().isEmpty() ->
                    etScheduledDate.error = getString(R.string.validate_mypet_date)

                selectedTimeRadioBtn == null ->
                    Snackbar.make(createLinearLayout, R.string.validate_mypet_time, Snackbar.LENGTH_SHORT).show()
                else -> {
                    // continue to step 3
                    showMypetDataToConfirm()
                    cvStep2.visibility = View.GONE
                    cvStep3.visibility = View.VISIBLE
                }
            }

        }


        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Cita registrada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }

        loadSpecialties()

        val doctorOptions = arrayOf("Doctor A", "Doctor B", "Doctor C")
        spinnerDoctors.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorOptions)
    }

    private fun loadSpecialties() {
        val call = apiService.getSpecialties()
        call.enqueue(object: Callback<ArrayList<Specialty>> {
            override fun onFailure(call: Call<ArrayList<Specialty>>, t: Throwable) {
                Toast.makeText(this@CreateActivity, "Ocurrió un problema al cargar las especialidades", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onResponse(call: Call<ArrayList<Specialty>>, response: Response<ArrayList<Specialty>>) {
                if (response.isSuccessful) { // [200...300]
                    val specialties = response.body()
                    val specialtyOptions = ArrayList<String>()

                    specialties?.forEach {
                        specialtyOptions.add(it.name)
                    }
                    spinnerSpecialties.adapter = ArrayAdapter<String>(this@CreateActivity, android.R.layout.simple_list_item_1, specialtyOptions)
                }
            }

        })
    }

    private fun showMypetDataToConfirm() {
        tvConfirmDescription.text = etDescription.text.toString()
        tvConfirmSpecialty.text = spinnerSpecialties.selectedItem.toString()

        val selectedRadioBtnId = radioGroupType.checkedRadioButtonId
        val selectedRadioType = radioGroupType.findViewById<RadioButton>(selectedRadioBtnId)

        tvConfirmType.text = selectedRadioType.text.toString()

        tvConfirmDoctorName.text = spinnerDoctors.selectedItem.toString()
        tvConfirmDate.text = etScheduledDate.text.toString()
        tvConfirmTime.text = selectedTimeRadioBtn?.text.toString()
    }

    fun onClickScheduledDate(v: View?) {
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            // Toast.makeText (this, "y-m-d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y, m, d)
            etScheduledDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    m.twoDigits(),
                    d.twoDigits()
                )
            )
            etScheduledDate.error = null
            displayRadioButtons()
        }

        // min date
        // max date
            val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)
            val datePicker = datePickerDialog.datePicker

            // set limits
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            datePicker.minDate = calendar.timeInMillis // +1
            calendar.add(Calendar.DAY_OF_MONTH, 29)
            datePicker.maxDate = calendar.timeInMillis // +30

            // show dialog
            datePickerDialog.show()
    }

    private fun displayRadioButtons() {
        //          radioGroup.clearCheck()
        //          radioGroup.removeAllViews()
        //          radioGroup.checkedRadioButtonId
        selectedTimeRadioBtn = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        val hours = arrayOf("3:00 PM", "4:OO PM", "5:00 PM", "6:00 PM")
        var goToLeft = true

        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it

            radioButton.setOnClickListener { view ->
                selectedTimeRadioBtn?.isChecked = false
                selectedTimeRadioBtn = view as RadioButton?
                selectedTimeRadioBtn?.isChecked = true
            }


            if (goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft
        }

    }

    private fun Int.twoDigits()
            = if (this>=10) this.toString() else "0$this"

    override fun onBackPressed() {
        when {
            cvStep3.visibility == View.VISIBLE -> {
                cvStep3.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE

            }
            cvStep2.visibility == View.VISIBLE -> {
                cvStep2.visibility = View.GONE
                cvStep1.visibility = View.VISIBLE

            }
            cvStep1.visibility == View.VISIBLE -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Está seguro que desea salir?")
                builder.setMessage("Si abandona el registro, los datos ingresados se perderán")
                builder.setPositiveButton("Salir") { _, _ ->
                    finish()
                }
                builder.setNegativeButton("Continuar con el resgitro") { dialog, _ ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()

            }
        }
    }
}
