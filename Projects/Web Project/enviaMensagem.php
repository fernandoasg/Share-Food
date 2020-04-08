<?php
header("Content-type: text/html; charset=utf-8");

$nome = $_POST["nome"];
$email = $_POST["email"];
$mensagem = $_POST["mensagem"];

$texto = "<h3> Formulario recebido pelo site! </h3><br>
			<b>Nome do Cliente:</b> $nome<br>
			<b>E-mail:</b> $email<br>
			<b>Mensagem:</b> $mensagem<br>";

$emailDestino = "contato@isharefood.com.br";
$headers = "MIME-Version: 1.0\r\n";
$headers .= "Content-type: text/html; charset=utf-8\r\n";
$headers .= "From:" . $email . "\r\n";

mail($emailDestino, 'Formulario recebido!', $texto, $headers);


$headers2 = "MIME-Version: 1.0\r\n";
$headers2 .= "Content-type: text/html; charset=utf-8\r\n";
$headers2 .= "From:" . $emailDestino . " \r\n";
$texto = "Olá, $nome<br>
			Seu e-mail foi recebido por um de nossos atendentes<br>
			Em breve será respondido!<br>
			<br>
			Agradecemos o interesse!<br>
			<br>
			Equipe Share Food<br>
			<br>";
mail($email, 'Recebemos seu email =)', $texto, $headers2);

print '<script>location.href= "http://www.isharefood.com.br/#contato";</script>';

?>