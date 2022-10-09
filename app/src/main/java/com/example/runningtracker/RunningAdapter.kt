package com.example.runningtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RunningAdapter(private val context: Context, private val running:List<Running>):
    RecyclerView.Adapter<RunningAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.run_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val run_activity = running[position]

        holder.activityTV.text = run_activity.activity
        holder.distanceTV.text = run_activity.distance.toString() + " miles"
        holder.durationTV.text = run_activity.duration.toString() + " min"
        holder.experienceTV.text = "Experience: " + run_activity.experience.toString() + "/10"
    }

    override fun getItemCount() = running.size

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
                val activityTV: TextView
                val distanceTV: TextView
                val durationTV: TextView
                val experienceTV: TextView

                init{
                    activityTV = itemView.findViewById(R.id.activity)
                    distanceTV = itemView.findViewById(R.id.distance)
                    durationTV = itemView.findViewById(R.id.duration)
                    experienceTV = itemView.findViewById(R.id.experience)
                }

        }
}