package shah.nishant.findingfalcone.game.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import shah.nishant.findingfalcone.R
import shah.nishant.findingfalcone.databinding.GameResultDialogBinding
import shah.nishant.findingfalcone.extensions.navigate
import shah.nishant.findingfalcone.extensions.viewLifecycleScoped

class GameResultDialog : DialogFragment() {

    private val args: GameResultDialogArgs by navArgs()
    private val binding: GameResultDialogBinding by viewLifecycleScoped(GameResultDialogBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return GameResultDialogBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.response.isSuccessful()) {
            binding.apply {
                result.setImageResource(R.drawable.win)
                title.setText(R.string.win_title)
                message.text = getString(R.string.win_message, args.response.planeName)
            }
        } else {
            binding.apply {
                result.setImageResource(R.drawable.lose)
                title.setText(R.string.lose_title)
                message.setText(R.string.lose_message)
            }
        }
        binding.playAgain.setOnClickListener {
            navigate(GameResultDialogDirections.playAgain())
            activity?.finish()
        }
    }

}
