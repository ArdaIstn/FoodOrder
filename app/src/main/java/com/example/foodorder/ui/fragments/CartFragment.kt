package com.example.foodorder.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentCartBinding
import com.example.foodorder.ui.adapter.CartListAdapter
import com.example.foodorder.ui.viewmodel.CartViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartFoodsAdapter: CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emptyAnim.visibility = View.VISIBLE

        observeCartFood()
        observeTotalPrice()

        binding.btnConfirm.setOnClickListener {
            showClearCartDialog()
        }

    }

    override fun onResume() {
        super.onResume()
        cartViewModel.getFoodsInCart("arda_isitan")
    }

    private fun setUpRecyclerView() {
        cartFoodsAdapter = CartListAdapter(cartViewModel)
        binding.rvCart.apply {
            adapter = cartFoodsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeCartFood() {
        cartViewModel.cartFood.observe(viewLifecycleOwner) { cartFood ->
            cartFoodsAdapter.submitList(cartFood)
            // Sepet öğelerini kontrol et
            if (cartFood.isEmpty()) {
                // Sepet boş, anim görünür yap
                binding.emptyAnim.visibility = View.VISIBLE
            } else {
                // Sepet dolu, anim'ı gizle
                binding.emptyAnim.visibility = View.GONE
            }
        }
    }

    private fun observeTotalPrice() {
        cartViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotalPriceCart.text =
                getString(R.string.cart_total_price, totalPrice.toString())
        }
    }

    private fun showClearCartDialog() {
        // AlertDialog ile kullanıcıya sepeti temizleme onayı sor
        MaterialAlertDialogBuilder(requireContext()).setTitle("Onayla")
            .setMessage("Sepeti Onaylıyor Musunuz?").setPositiveButton("Evet") { dialog, which ->
                // 'Evet' seçildiğinde sepeti temizle
                cartViewModel.deleteAllFromCart("arda_isitan")
                Snackbar.make(requireView(), "Siparişiniz Alınmıştır", Snackbar.LENGTH_SHORT).show()
            }.setNegativeButton("Hayır") { dialog, which ->
                // 'Hayır' seçildiğinde sadece dialogu kapat
                dialog.dismiss()
            }.show()
    }


}