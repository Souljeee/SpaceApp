package com.example.spaceapp.ui.main.TODOActivity

import android.app.ProgressDialog.show
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceapp.databinding.ActivityTodoBinding


class TODOActivity : AppCompatActivity() {

    private lateinit var answer : String

    private lateinit var binding:ActivityTodoBinding
    private lateinit var todoAdapter : TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initFAB()
    }

    private fun initRecyclerView() = with(binding){
        todoList.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this@TODOActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        todoList.layoutManager = layoutManager

        todoAdapter  = TodoAdapter(supportFragmentManager)
        todoList.adapter = todoAdapter

        todoAdapter.setTasks(getTasks())
    }

    private fun initFAB() = with(binding){
        addButton.setOnClickListener {
            val dlgFragment : DialogFragment = DialogEditerFragment(TodoAdapter.CREATE_FLAG)
            dlgFragment.show(supportFragmentManager,"tag")
        }
    }

    fun onDialogResult(resultDialog: String,flag:Int,position:Int) {
        answer = resultDialog
        if(flag == TodoAdapter.CREATE_FLAG){
            todoAdapter.addTask(Task(answer))
        }else{
            todoAdapter.changeTask(position,Task(answer))
        }
    }


    private fun getTasks() : MutableList<Task>{
        return mutableListOf(
            Task("Почистить зубы"),
            Task("Сходить в магазин"),
            Task("Сделать дз"),
            Task("Помыть посуду"),
        )
    }
}