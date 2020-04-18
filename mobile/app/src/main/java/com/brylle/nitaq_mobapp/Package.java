package com.brylle.nitaq_mobapp;

import android.util.Log;

import java.util.ArrayList;

public class Package {

    // class to represent event entries in the events page of the app
    // the following private members are the same as the fields of an event entry in a database
    private String subject;
    private String topic;
    private String module;
    private ArrayList<String> concept_contents;
    private ArrayList<String> concept_questions;
    private ArrayList<String> answers;
    private ArrayList<String> correct_answers;
    private ArrayList<String> concept_titles;
    private boolean downloaded;

    // constructor
    public Package(
            String subject,
            String topic,
            String module,
            ArrayList<String> concept_contents,
            ArrayList<String> concept_questions,
            ArrayList<String> answers,
            ArrayList<String> correct_answers,
            ArrayList<String> concept_titles,
            boolean downloaded) {
        this.subject = subject;
        this.topic = topic;
        this.module = module;
        this.concept_contents = concept_contents;
        this.concept_questions = concept_questions;
        this.answers = answers;
        this.correct_answers = correct_answers;
        this.concept_titles = concept_titles;
        this.downloaded = downloaded;
    }

    // prints a log output of the event object
    public void print() {
        Log.d("Debug", "Package of {" + this.subject + ", " + this.topic + ", " + this.module + "} added!");
    }

    public String getSubject() {
        return subject;
    }

    public String getTopic() {
        return topic;
    }

    public String getModule() {
        return module;
    }

    public ArrayList<String> getConcept_contents() {
        return concept_contents;
    }

    public ArrayList<String> getConcept_questions() {
        return concept_questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getCorrect_answers() {
        return correct_answers;
    }

    public ArrayList<String> getConcept_titles() {
        return concept_titles;
    }

    public boolean getDownloaded() {
        return downloaded;
    }

}

