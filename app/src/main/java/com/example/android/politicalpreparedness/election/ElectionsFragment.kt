package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment : Fragment() {

    private var electionAdapter: ElectionListAdapter? = null

    private val _viewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, ElectionsViewModelFactory(activity.application))
            .get(ElectionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentElectionBinding.inflate(inflater)

        _viewModel.populateElections()

        binding.lifecycleOwner = this
        binding.viewModel = _viewModel

        electionAdapter = ElectionListAdapter(ElectionListener {
        })
        binding.electionsRecyclerView.adapter = electionAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.elections.observe(viewLifecycleOwner) {
            electionAdapter?.submitList(it)
        }
    }

    // TODO: Refresh adapters when fragment loads
}