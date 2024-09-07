package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.utils.setDisplayUpButton

class VoterInfoFragment : Fragment() {

    private val _viewModel: VoterInfoViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, VoterInfoViewModelFactory(activity.application))
            .get(VoterInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
        binding.executePendingBindings()

        val args = VoterInfoFragmentArgs.fromBundle(requireArguments())
        val division = args.argDivision
        val electionId = args.argElectionId
        val state = division.state.ifEmpty { "ri" }

        _viewModel.populateVoterInfo(state, electionId)

        _viewModel.dataNotAvailable.observe(viewLifecycleOwner) { isDataNotAvailable ->
            isDataNotAvailable?.let {
                if (isDataNotAvailable) {
                    // Show error and go back
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_election_detail_not_found), Toast.LENGTH_SHORT
                    )
                        .show()
                    findNavController().navigateUp()
                    _viewModel.finishFetching()
                }
            }
        }

        _viewModel.votingLocationUrl.observe(viewLifecycleOwner) { url ->
            openWebView(url)
        }

        _viewModel.ballotInfoUrl.observe(viewLifecycleOwner) { url ->
            openWebView(url)
        }

        _viewModel.showToast.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                _viewModel.showToastCompleted()
            }
        }

        _viewModel.isElectionFollowed.observe(viewLifecycleOwner) { isFollowed ->
            isFollowed?.let {
                binding.followElectionButton.text =
                    if (isFollowed) {
                        requireContext().getString(R.string.unfollow_election_button_text)
                    } else {
                        requireContext().getString(R.string.follow_election_button_text)
                    }
            }
        }

        setDisplayUpButton(true)

        return binding.root
    }

    private fun openWebView(url: String?) {
        url?.let {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}