<!DOCTYPE html>
<html>
<style>
    @import url("../../css/styles.css");
</style>

<head>
    <script type="text/javascript" src="../js_libs/jquery.min.js"></script>
    <script type="module" src="model_input.js"></script>
</head>

<body>
<div style="text-align:center; margin-top: 150px;">
    <?php

    include_once "../../connect_to_db.php";

    $id = $_GET["id"];
    echo "<form action='update_table.php?id=" . $id . "' METHOD=POST>";

    include("get_table_input_form.php");

    echo "<p><button style='padding: 10px 10px; width: 80px;' type='submit'>Update</button></p>";
    echo "</form>";

    $update = !empty($_POST["number"]);
    if (!$update) die();

    $format = "update cars_v2 set owner_id = %s, model_id = %s, 
    state = '%s', license_plate_number = '%s' where id = %d";

    $query = sprintf($format, $_POST["owner_id"], $_POST["model_id"], 
    $_POST["state"], $_POST["number"], $id);

    try {
        $conn->query($query);
    } catch (Exception $e) {
        die('Error: ' . $e->getMessage());
    }

    header("Location: index.php");

    ?>
    
    <a href="index.php"><h1>Назад</h1></a>
    </div>
</body>

</html>