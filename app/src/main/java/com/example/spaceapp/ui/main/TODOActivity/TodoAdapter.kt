package com.example.spaceapp.ui.main.TODOActivity

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceapp.R


class TodoAdapter(val fragmentManager:FragmentManager): RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private lateinit var holder : ViewHolder
    private lateinit var tasks : MutableList<Task>

    fun setTasks(tasks: MutableList<Task>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun addTask(task : Task){
        tasks.add(0,task)
        notifyItemInserted(0)
    }

    fun removeTask(position: Int){
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }
    fun changeTask(position: Int,task : Task){
        tasks[position] = task
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout,parent,false)
        holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val task:CheckBox = itemView.findViewById(R.id.task_checkbox)
        private val editButton : ImageButton = itemView.findViewById(R.id.edit_button)

        fun bind(taskData : Task){
            task.isChecked = false
            task.text = taskData.name
            task.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked){
                    val string = SpannableString(compoundButton.text)
                    string.setSpan(StrikethroughSpan(), 0, string.length, 0)
                    compoundButton.text = string
                    removeTask(adapterPosition)
                } else{
                    compoundButton.text = taskData.name
                }
            }

            editButton.setOnClickListener {
                val dlgFragment : DialogFragment = DialogEditerFragment(CHANGE_FLAG,adapterPosition)
                dlgFragment.show(fragmentManager,"tag")
            }
        }
    }

    companion object{
        public const val CREATE_FLAG : Int  = 0
        const val CHANGE_FLAG : Int  = 1
    }

}