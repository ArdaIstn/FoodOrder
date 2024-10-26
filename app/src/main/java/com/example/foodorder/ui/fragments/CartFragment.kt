package com.example.foodorder.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentCartBinding
import com.example.foodorder.ui.adapter.CartListAdapter
import com.example.foodorder.ui.swipetodelete.SwipeToDelete
import com.example.foodorder.ui.viewmodel.CartViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartFoodsAdapter: CartListAdapter
    private var isCartEmpty: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCartFood()
        observeTotalPrice()

        binding.btnConfirm.setOnClickListener {
            if (isCartEmpty) {
                Snackbar.make(view, "Sepetiniz boş, sipariş veremezsiniz!", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                showClearCartDialog()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cartViewModel.getFoodsInCart("arda_isitan")
    }

    private fun setUpRecyclerView() {
        cartFoodsAdapter = CartListAdapter(emptyList(), cartViewModel)
        binding.rvCart.apply {
            adapter = cartFoodsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeCartFood() {
        cartViewModel.cartFood.observe(viewLifecycleOwner) { cartFood ->
            cartFoodsAdapter = CartListAdapter(cartFood, cartViewModel)
            binding.rvCart.adapter = cartFoodsAdapter
            isCartEmpty = cartFood.isEmpty()
            binding.emptyAnim.visibility = if (isCartEmpty) View.VISIBLE else View.GONE
        }
    }

    private fun observeTotalPrice() {
        cartViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotalPriceCart.text =
                getString(R.string.cart_total_price, totalPrice.toString())
        }
    }

    private fun showClearCartDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Onayla")
            .setMessage("Sepeti Onaylıyor Musunuz?").setPositiveButton("Evet") { dialog, _ ->
                cartViewModel.deleteAllFromCart("arda_isitan")
                Snackbar.make(requireView(), "Siparişiniz Alınmıştır", Snackbar.LENGTH_SHORT).show()
                showAnimationDialog()
            }.setNegativeButton("Hayır") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showAnimationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.order_anim)
        dialog.setCanceledOnTouchOutside(false)
        val animationView = dialog.findViewById<LottieAnimationView>(R.id.order_anim)
        val json =
            resources.openRawResource(R.raw.order_received).bufferedReader().use { it.readText() }
        LottieCompositionFactory.fromJsonString(json, "order_anim.json")
            .addListener { lottieResult ->
                lottieResult?.let { composition ->
                    animationView.setComposition(composition)
                    animationView.speed = (composition.duration / 2000f)
                    animationView.playAnimation()
                    dialog.show()
                    animationView.postDelayed({ if (dialog.isShowing) dialog.dismiss() }, 3000)
                }
            }
    }


}
