$(document).ready(function() {
  var currentLesson = 0;
  addLesson();

  var correct_answers = [];

  function addLesson() {
    currentLesson++;
    var c = currentLesson.toString();
    var string =
      '<div style="padding-left:2em"><legend>- Concept ' +
      c +
      '</legend> <div class="lesson form-group"> <div class="row" > <div class="col"> <label for="title">Concept Title:</label> <input type="text" name="title" class="title form-control" /><br /> <label for="content">Concept Content:</label> <textarea rows="10" type="text" name="content" class="content form-control" ></textarea> <br /> </div> <div class="col"> <label for="question">Review Question:</label> <textarea rows="2" type="text" name="question" class="question form-control" ></textarea ><br /> <p> Multiple Choice Answers (Please select the correct choice): </p> <label for="choices' +
      c +
      '" class="form-check-label" a>A. </label> <div class="form-inline"> <input type="radio" class="form-check-input mcq-radio col-1" name="choices' +
      c +
      '" value="a" /> <input type="text" name="choices-input" class="form-control col" /> </div> <label for="choices' +
      c +
      '" class="form-check-label" a>B. </label> <div class="form-inline"> <input type="radio" class="form-check-input mcq-radio col-1" name="choices' +
      c +
      '" value="b" /> <input type="text" name="choices-input" class="form-control col" /> </div> <label for="choices' +
      c +
      '" class="form-check-label" a>C. </label> <div class="form-inline"> <input type="radio" class="form-check-input mcq-radio col-1" name="choices' +
      c +
      '" value="c" /> <input type="text" name="choices-input" class="form-control col" /> </div> <label for="choices' +
      c +
      '" class="form-check-label" a>D. </label> <div class="form-inline"> <input type="radio" class="form-check-input mcq-radio col-1" name="choices' +
      c +
      '" value="d" /> <input type="text" name="choices-input" class="form-control col" /> </div> </div> </div> </div></div>';

    $("#lessons").append(string);
  }

  //Add new lesson
  $("#new-lesson").click(function() {
    addLesson();
  });

  //Submit button, aggregate data
  $("#submit").click(function() {
    var allAnswered = true;
    var allAnswered = true;
    //Check if all fields have been populated
    $("input:input").each(function() {
      if ($(this).val() === "") {
        allAnswered = false;
      }
    });
    for (let i = 0; i < currentLesson; i++) {
      if (
        !$('input[name="choices' + (i + 1).toString() + '"]').is(":checked")
      ) {
        allAnswered = false;
      }
    }
    if (!allAnswered) {
      alert("Please fill out all the fields");
      allAnswered = true;
      return;
    }
    var subject, topic, module, template, correct, lessons;
    var titles = [],
      contents = [],
      questions = [],
      correct_answers = [],
      answers = [];

    subject = document.getElementById("subject").value;
    topic = document.getElementById("topic").value;
    module = document.getElementById("module").value;
    template = $('input[name="template-option"]:checked').val();

    //Get lesson title
    var title_objects = document.getElementsByClassName("title");

    for (let i = 0; i < title_objects.length; i++) {
      titles.push(title_objects[i].value);
    }

    //Get lesson content
    var content_objects = document.getElementsByClassName("content");

    for (let i = 0; i < content_objects.length; i++) {
      contents.push(content_objects[i].value);
    }

    //Get review questions
    var question_objects = document.getElementsByClassName("question");

    for (let i = 0; i < question_objects.length; i++) {
      questions.push(question_objects[i].value);
    }

    // get answers
    var answers_objects = $('input[name = "choices-input"]');

    for (let i = 0; i < currentLesson; i++) {
      // assuming there is always 4 options per question
      var answer = [];
      for (let j = 0; j < 4; j++) {
        answer.push(answers_objects[i * 4 + j].value);
      }
      var answer_string = answer.join("|");
      answers.push(answer_string);
    }

    // for (let i = 0; i < answers_objects; i++) {
    //     answers.push(answers_objects[i].value);
    // }

    //Get correct answer
    for (let i = 0; i < currentLesson; i++) {
      var correct_answer = $(
        'input[name="choices' + (i + 1).toString() + '"]:checked'
      ).val();
      correct_answers.push(correct_answer);
    }

    //Data to be sent to firebase
    var course = {
      subject: subject,
      topic: topic,
      adventure: module,
      template: template,
      concept_titles: titles,
      concept_contents: contents,
      concept_questions: questions,
      answers: answers,
      correct_answers: correct_answers
    };

    console.log(course);

    function sucessScreen() {
      alert("You have sucessfully submitted " + module);
      location.reload();
    }
    function errorScreen() {
      alert("Error: " + module+" not sucessfully submited. Please try again");
    }

    // store course in firebase with an auto-generated id, or 'db.collection("packages").doc("courseId").set(course)' if you want manual id for course
    db.collection("packages")
      .add(course)
      .then(function() {
        console.log("Document successfully written!");
        sucessScreen();
      })
      .catch(function(error) {
        console.error("Error writing document: ", error);
        errorScreen();
      });
  });
});
