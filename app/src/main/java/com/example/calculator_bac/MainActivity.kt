package com.example.calculator_bac

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.calculator_bac.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.widget.RadioButton
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> clearValues()
            else -> super.onOptionsItemSelected(item)
        }
        return true;
    }
    fun calculateBAC(view: View) {
        //rho factor is the factor based on gender in the BAC formula
        var rhoFactor = .73;
        //Format end calculation
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        if(binding.radioGirl.isChecked) {
            rhoFactor = .66;
        }
        //Make sure there are no null responses
        if(!binding.editTextBeer.text.isNullOrEmpty()||!binding.editTextWeight.text.isNullOrEmpty()||!binding.editTextMixedShots.text.isNullOrEmpty()||!binding.editTextStrongShot.text.isNullOrEmpty()||!binding.editTextWine.text.isNullOrEmpty()||!binding.editTextHour.text.isNullOrEmpty()) {
            //The formula
            var calculateValue = (binding.editTextBeer.text.toString().toFloat() + binding.editTextWine.text.toString().toFloat() + binding.editTextStrongShot.text.toString().toFloat() + binding.editTextMixedShots.text.toString().toFloat()/2) * 0.6 *5.14/ binding.editTextWeight.text.toString().toFloat() * rhoFactor -(.015 * binding.editTextHour.text.toString().toFloat())
           //Change response based on level of alcohol drank
            if(calculateValue > .08)
            binding.textViewAnswer.text = "Your ABV is " + df.format(calculateValue) +"%\n You should not drive until your ABV drops below 0.08";
            else if(calculateValue <=0)
                binding.textViewAnswer.text = "Your ABV is 0% \n Have a nice safe night!";
            else
                binding.textViewAnswer.text = "Your ABV is " + df.format(calculateValue) + "% \n Have a nice safe night!"
        }
        else {
            binding.textViewAnswer.text = "Fill out all boxes to get a calculation"
        }

    }
    //Reset all values
   fun clearValues() : Boolean {
        binding.editTextBeer.text.clear()
        binding.editTextHour.text.clear()
        binding.editTextMixedShots.text.clear()
        binding.editTextStrongShot.text.clear()
        binding.editTextWine.text.clear()
        binding.editTextWeight.text.clear()
        binding.textViewAnswer.text = "";
       return true;
    }
    override fun onSupportNavigateUp(): Boolean {
       val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
        return true;
    }
}