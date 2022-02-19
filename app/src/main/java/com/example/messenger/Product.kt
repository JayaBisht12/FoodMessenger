package com.example.messenger

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.data.ProductService
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Product.newInstance] factory method to
 * create an instance of this fragment.
 */
class Product : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_product, container, false)
        val recyclerview = v.findViewById<RecyclerView>(R.id.recyclerview)
        val value= ArrayList<ItemsViewModel>()
        val tvLoading=v.findViewById<TextView>(R.id.tvLoading)
         val pBar=v.findViewById<ProgressBar>(R.id.pBar)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)
        val pro = ProductService.productsInstance.getProducts()
        pro.enqueue(object : retrofit2.Callback<List<Products>?> {
            override fun onResponse(
                call: Call<List<Products>?>,
                response: Response<List<Products>?>
            ) {
                val data=response.body()!!//data from the server





                for (i in 0..data.size-1) {

                  //  stringbuilder.append(data[i].id)
                    value.add(ItemsViewModel(data[i].title,data[i].description,data[i].price.toString(),data[i].image))
                    //data.add(Products(pro.))


                }
                val adapter = CustomAdapter(value)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
                tvLoading.visibility=View.GONE
                pBar.visibility=View.GONE
                recyclerview.visibility=View.VISIBLE
            }

            override fun onFailure(call: Call<List<Products>?>, t: Throwable) {

                tvLoading.text="Oops Something went wrong!"
                pBar.visibility=View.GONE
                Toast.makeText(context, "Failed to retrieve details " + t.message, Toast.LENGTH_SHORT).show()
            }
        })






        // This loop will create 20 Views containing
        // the image with the count of view





        return v;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Product.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Product().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
