package com.example.myapplication;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

public class Bot extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private TextInputEditText userInputEditText;
    LinearLayout chatHistoryLayout;
    Activity activity;
    private GenerativeModel model;
    GenerationConfig config;
    List<Content> chatHistory;
    List<String> messages;
    ChatFutures chat;
    FloatingActionButton sendButton;


    private String mParam1;
    private String mParam2;

    public Bot() {
    }

    public static Bot newInstance(String param1, String param2) {
        Bot fragment = new Bot();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot, container, false);
        userInputEditText = view.findViewById(R.id.user_message);
        sendButton = view.findViewById(R.id.send_btn);
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        connectWithGemini();

        chatAdapter = new ChatAdapter(chatHistory);
        chatRecyclerView.setAdapter(chatAdapter );

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuestion = userInputEditText.getText().toString().trim();
                if (userQuestion.isEmpty()) {
                    return;
                }
                userInputEditText.setText("");
                appendMessage(userQuestion, "user");
                sendMessageToAi(userQuestion);
            }
        });

        return view;
    }

    private void connectWithGemini() {
        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        configBuilder.temperature = 0.9f;
        configBuilder.topK = 16;
        configBuilder.topP = 0.1f;
        configBuilder.maxOutputTokens = 5120;
        configBuilder.stopSequences = Arrays.asList("red");

        GenerationConfig generationConfig = configBuilder.build();

        GenerativeModel gm = new GenerativeModel(
                "gemini-1.0-pro",
                "AIzaSyCW3-YNJfwTJBR-hAMuGB5Y08RbR1sSL1Q",
                generationConfig
        );

        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content.Builder userContentBuilder = new Content.Builder();
        userContentBuilder.setRole("user");
        userContentBuilder.addText("your name is sehat.ai which is a bot that is used by patient. Your job is to give the patients information releated to fitness, workout, diet plan. You also have to provide the user diagnosis by taking all the symptoms from them. You are a doctor. You also have to explain the prescription provided by the patient.");
        Content userContent = userContentBuilder.build();

        Content.Builder modelContentBuilder = new Content.Builder();
        modelContentBuilder.setRole("model");
        modelContentBuilder.addText("Welcome to Sehat.ai!\n" +
                "I am Sehat.ai, a virtual health assistant designed to help you achieve your health and fitness goals. I can provide you with personalized advice on:\n" +
                "Fitness: Workouts, exercises, and training plans\n" +
                "Nutrition: Diet plans, meal ideas, and recipes\n" +
                "Diagnosis: Medical symptoms and conditions\n" +
                "Diagnosis:\n" +
                "To provide you with an accurate diagnosis, please provide me with a detailed description of your symptoms. I will ask you a series of questions to gather all the necessary information. Based on your responses, I will provide you with a list of possible diagnoses and recommend the appropriate course of action, such as visiting a doctor or taking over-the-counter medications.\n" +
                "Prescription Explanation:\n" +
                "If you have a prescription from your doctor, I can help you understand it. Simply upload a picture of the prescription or enter the details manually. I will provide you with information on:\n" +
                "Medication name: Generic and brand names\n" +
                "Dosage and frequency: How much to take and how often\n" +
                "Side effects: Potential adverse reactions\n" +
                "Drug interactions: Medications that should not be taken together\n" +
                "Disclaimer:\n" +
                "I am a virtual health assistant and not a substitute for professional medical advice. Always consult with a licensed healthcare professional for any health concerns or before making any medical decisions.");
        Content modelContent = modelContentBuilder.build();

        chatHistory = new ArrayList<>();
        messages = new ArrayList<>();
        messages.add("Welcome to Sehat.ai!\\n\" +\n" +
                "                \"I am Sehat.ai, a virtual health assistant designed to help you achieve your health and fitness goals. I can provide you with personalized advice on:\\n\" +\n" +
                "                \"Fitness: Workouts, exercises, and training plans\\n\" +\n" +
                "                \"Nutrition: Diet plans, meal ideas, and recipes\\n\" +\n" +
                "                \"Diagnosis: Medical symptoms and conditions\\n\" +\n" +
                "                \"Diagnosis:\\n\" +\n" +
                "                \"To provide you with an accurate diagnosis, please provide me with a detailed description of your symptoms. I will ask you a series of questions to gather all the necessary information. Based on your responses, I will provide you with a list of possible diagnoses and recommend the appropriate course of action, such as visiting a doctor or taking over-the-counter medications.\\n\" +\n" +
                "                \"Prescription Explanation:\\n\" +\n" +
                "                \"If you have a prescription from your doctor, I can help you understand it. Simply upload a picture of the prescription or enter the details manually. I will provide you with information on:\\n\" +\n" +
                "                \"Medication name: Generic and brand names\\n\" +\n" +
                "                \"Dosage and frequency: How much to take and how often\\n\" +\n" +
                "                \"Side effects: Potential adverse reactions\\n\" +\n" +
                "                \"Drug interactions: Medications that should not be taken together\\n\" +\n" +
                "                \"Disclaimer:\\n\" +\n" +
                "                \"I am a virtual health assistant and not a substitute for professional medical advice. Always consult with a licensed healthcare professional for any health concerns or before making any medical decisions.");
        chatHistory.add(userContent);
        chatHistory.add(modelContent);


        chat = model.startChat(chatHistory);

    }
    private void sendMessageToAi(String userQuestion) {
        Content.Builder messageBuilder = new Content.Builder();
        messageBuilder.setRole("user");
        messageBuilder.addText(userQuestion);
        Content message = messageBuilder.build();


        chatHistory.add(message);
        messages.add(String.valueOf(message));

        ListenableFuture<GenerateContentResponse> response = chat.sendMessage(message);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String messageFromAi = result.getText();
                Content.Builder aiMessageBuilder = new Content.Builder();
                aiMessageBuilder.setRole("model");
                aiMessageBuilder.addText(messageFromAi);
                Content aiContent = aiMessageBuilder.build();
                chatHistory.add(aiContent);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        appendMessage(messageFromAi, "model");
                        messages.add(messageFromAi);
                    }
                });

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Error sending message", Objects.requireNonNull(t.getMessage()));
            }
        }, Executors.newSingleThreadExecutor());
    }
    @SuppressLint("ResourceType")
    private void appendMessage(String message, String role) {
        messages.add(message);
        chatAdapter.notifyDataSetChanged();
        chatRecyclerView.smoothScrollToPosition(messages.size());
    }

}