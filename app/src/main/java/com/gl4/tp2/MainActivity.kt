package com.gl4.tp2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText


    val spinner : Spinner by lazy { findViewById(R.id.spinner) }
    val spinner2 : Spinner by lazy { findViewById(R.id.spinner2) }

    class Student(val firstName: String, val lastName: String, val gender: String, val matiere:String,var isPresent: Boolean = true) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var studentList= arrayListOf<Student>()



        val random = Random.Default
        for (i in 1..10) {
            val firstName = "Prénom$i"
            val lastName = "Nom$i"
            val gender = if (random.nextBoolean()) "Homme" else "Femme"
            val matiere=if (random.nextBoolean()) "Cours" else "TP"
            val isPresent=if (random.nextBoolean()) true else false
            studentList.add(Student(firstName, lastName, gender,matiere,isPresent))
        }
        studentList.add(Student("mohamed", "mortadha", "Homme","TP",false))


        var adapter1=StudentListAdapter(studentList)
        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.apply {
            adapter = adapter1
            layoutManager = LinearLayoutManager(this@MainActivity)
        }


        editText=findViewById(R.id.editText)

        var matieres = listOf<String>("Cours","TP")

        var etats = listOf<String>("Présent","Absent")

        spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,matieres)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item=matieres.get(position);
                Toast.makeText(this@MainActivity,item,Toast.LENGTH_SHORT).show()


                adapter1.filter.filter("0,".plus(item.toString()))


            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }


        spinner2.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,etats)

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item=etats.get(position);
                Toast.makeText(this@MainActivity,item,Toast.LENGTH_SHORT).show()


                adapter1.filter.filter("2,".plus(item.toString()))


            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }









            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Avant que le texte change, si vous avez besoin de gérer quelque chose.
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Lorsque le texte change, déclenchez la mise à jour du filtre.
                    adapter1.filter.filter("1,".plus(s.toString()))


                }

                override fun afterTextChanged(s: Editable?) {
                    // Après que le texte a changé, si vous avez besoin de gérer quelque chose.
                }
            })







        }

}
