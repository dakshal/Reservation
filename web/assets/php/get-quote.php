<?php
if ((isset($_POST['name'])) && (strlen(trim($_POST['name'])) > 0)) {
	$name = stripslashes(strip_tags($_POST['name']));
} else {$name = 'No name entered';}
if ((isset($_POST['email'])) && (strlen(trim($_POST['email'])) > 0)) {
	$email = stripslashes(strip_tags($_POST['email']));
} else {$email = 'No email entered';}
if ((isset($_POST['phone'])) && (strlen(trim($_POST['phone'])) > 0)) {
    $phone = stripslashes(strip_tags($_POST['phone']));
} else {$phone = 'No phone entered';}
if ((isset($_POST['message'])) && (strlen(trim($_POST['message'])) > 0)) {
	$message = stripslashes(strip_tags($_POST['message']));
} else {$message = 'No phone entered';}
ob_start();
?>
<html>
<head>
<style type="text/css">
</style>
</head>
<body>
<table width="550" border="1" cellspacing="2" cellpadding="2">
  <tr bgcolor="#eeffee">
    <td>Name</td>
    <td><?php echo $name;?></td>
  </tr>
  <tr bgcolor="#eeeeff">
    <td>Email</td>
    <td><?php echo $email;?></td>
  </tr>
    <tr bgcolor="#eeeeff">
        <td>Phone</td>
        <td><?php echo $phone;?></td>
    </tr>
  <tr bgcolor="#eeffee">
    <td>Message</td>
    <td><?php echo $message;?></td>
  </tr>
</table>
</body>
</html>
<?php
$body = ob_get_contents();

$to = 'you@domain.com';
$toname = 'Your Name';
//$anotheraddress = 'email@example.com';
//$anothername = 'Another Name';

require("phpmailer.php");

$mail = new PHPMailer();

$mail->From     = $email;
$mail->FromName = $name;
$mail->AddAddress($to ,$toname); // Put your email
//$mail->AddAddress($anotheraddress,$anothername); // addresses here

$mail->WordWrap = 50;
$mail->IsHTML(true);

$mail->Subject  =  "Demo Form: Get Quote form submitted";
$mail->Body     =  $body;
$mail->AltBody  =  "Get Quote Request\n\n Name: ".$name."\n Phone: ".$phone."\n\n ".$message;

if(!$mail->Send()) {
	$recipient = $to;
	$subject = 'Get Quote form failed';
	$content = $body;	
  mail($recipient, $subject, $content, "From: $name\r\nReply-To: $email\r\nX-Mailer: DT_formmail");
  exit;
}
?>
