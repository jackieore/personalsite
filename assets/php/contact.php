<?php 

$name = $_POST["name"];
$lastname = $_POST["surname"];
$email = $_POST["email"];
$phone = $_POST["phone"];
$message = $_POST["message"];
$formcontent="From: $name $lastname\n Message: $message";
$recipient = "kayathom@gmail.com";
$subject = "Contact Form";
$mailheader = "Contact Form From: $email \r\n";
mail($recipient, $subject, $formcontent, $mailheader) or die("Error!");
echo "Thank You!";
?>
