package com.rasenyer.notesthree.ui.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.drjacky.imagepicker.ImagePicker
import com.rasenyer.notesthree.R
import com.rasenyer.notesthree.databinding.FragmentAddBinding
import com.rasenyer.notesthree.model.Note
import com.rasenyer.notesthree.ui.activity.MainActivity
import com.rasenyer.notesthree.vm.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private var imageUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        binding.mImageView.setOnClickListener {
            ImagePicker.with(context as Activity).crop().createIntentFromDialog { launcherImage.launch(it) }
        }

        binding.mMaterialCardViewDelete.setOnClickListener {

            imageUri = null
            binding.mImageView.setImageResource(R.drawable.ic_add)
            binding.mMaterialCardViewDelete.visibility = View.GONE

        }

        mButtonSave.setOnClickListener { insert() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val launcherImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data!!
            binding.mImageView.setImageURI(imageUri)
            binding.mMaterialCardViewDelete.visibility = View.VISIBLE
        }

    }

    private fun insert() {

        when{

            TextUtils.isEmpty(binding.mEditTextTitle.text.toString()) -> {
                binding.mEditTextTitle.error = resources.getString(R.string.enter_the_title)
            }
            TextUtils.isEmpty(binding.mEditTextDescription.text.toString()) -> {
                binding.mEditTextDescription.error = resources.getString(R.string.enter_the_description)
            }

            else -> {

                if (imageUri == null) {

                    val note = Note(
                        id          =   0,
                        title       =   binding.mEditTextTitle.text.toString(),
                        description =   binding.mEditTextDescription.text.toString(),
                        date        =   System.currentTimeMillis().toString(),
                        image       =   ""
                    )

                    noteViewModel.insert(note)
                    findNavController().navigate(R.id.action_AddFragment_to_HomeFragment)

                } else {

                    val note = Note(
                        id          =   0,
                        title       =   binding.mEditTextTitle.text.toString(),
                        description =   binding.mEditTextDescription.text.toString(),
                        date        =   System.currentTimeMillis().toString(),
                        image       =   imageUri.toString()
                    )

                    noteViewModel.insert(note)
                    findNavController().navigate(R.id.action_AddFragment_to_HomeFragment)

                }

            }

        }

    }

}