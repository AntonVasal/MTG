package com.example.mtg.ui.activities.mainActivity.countFragment;

import com.example.mtg.databinding.FragmentCountBinding;

public class CountViewsOperator {
    private final FragmentCountBinding binding;

    public CountViewsOperator(FragmentCountBinding binding) {
        this.binding = binding;
    }

    public void buttonEnabledFalse(int typeNumber) {
        if (typeNumber == 2){
            binding.buttonMinus.setEnabled(false);
        } else if(typeNumber == 3){
            binding.buttonForDecimals.setEnabled(false);
        }
        binding.deleteButton.setEnabled(false);
        binding.okButton.setEnabled(false);
        binding.button0.setEnabled(false);
        binding.button1.setEnabled(false);
        binding.button2.setEnabled(false);
        binding.button3.setEnabled(false);
        binding.button4.setEnabled(false);
        binding.button5.setEnabled(false);
        binding.button6.setEnabled(false);
        binding.button7.setEnabled(false);
        binding.button8.setEnabled(false);
        binding.button9.setEnabled(false);
    }

    public void buttonEnabledTrue(int typeNumber){
        if(typeNumber == 2){
            binding.buttonMinus.setEnabled(true);
        } else if (typeNumber == 3){
            binding.buttonForDecimals.setEnabled(true);
        }
        binding.deleteButton.setEnabled(true);
        binding.okButton.setEnabled(true);
        binding.button0.setEnabled(true);
        binding.button1.setEnabled(true);
        binding.button2.setEnabled(true);
        binding.button3.setEnabled(true);
        binding.button4.setEnabled(true);
        binding.button5.setEnabled(true);
        binding.button6.setEnabled(true);
        binding.button7.setEnabled(true);
        binding.button8.setEnabled(true);
        binding.button9.setEnabled(true);
    }

    public void typeInButtons(int typeNumber){
        if (typeNumber == 2){
            binding.buttonMinus.setOnClickListener(view -> {
                if (binding.userAnswerText.getText().toString().length() == 0){
                    binding.userAnswerText.setText("-");
                }else{
                    String answer = binding.userAnswerText.getText().toString().substring(0,1);
                    if (!answer.equals("-")){
                        String answerForText = "-" + binding.userAnswerText.getText().toString();
                        binding.userAnswerText.setText(answerForText);
                    }else {
                        binding.userAnswerText.setText(binding.userAnswerText.getText().toString()
                                .substring(1));
                    }
                }
            });
        }else if(typeNumber == 3){
            binding.buttonForDecimals.setOnClickListener(view -> {
                if(binding.userAnswerText.getText().toString().length() == 0){
                    binding.userAnswerText.setText("0.");
                }else{
                   String answer =  binding.userAnswerText.getText().toString();
                   char[] answerChars = answer.toCharArray();
                   int k = 0;
                    for (char answerChar : answerChars) {
                        if (answerChar == '.') {
                            k++;
                        }
                    }
                   if(k==0){
                       binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("."));
                   }
                }
            });
        }
        binding.button0.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("0")));
        binding.button1.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("1")));
        binding.button2.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("2")));
        binding.button3.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("3")));
        binding.button4.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("4")));
        binding.button5.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("5")));
        binding.button6.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("6")));
        binding.button7.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("7")));
        binding.button8.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("8")));
        binding.button9.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("9")));
        binding.deleteButton.setOnClickListener(view -> {
            if(binding.userAnswerText.getText().toString().length() != 0){
                String delete = binding.userAnswerText.getText().toString();
                binding.userAnswerText.setText(delete.substring(0,delete.length()-1));
            }
        });
    }
}
