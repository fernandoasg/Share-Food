<?php
$projectId = 'sharefood-d8ba5';

function set_document($projectId) {
    // Create the Cloud Firestore client
    $db = new FirestoreClient([
        'projectId' => $projectId,
    ]);
    # [START fs_set_document]
    $data = [
        'name' => 'Los Angeles',
        'state' => 'CA',
        'country' => 'USA'
    ];
    $db->collection('cities')->document('LA')->set($data);
    # [END fs_set_document]
    printf('Set data for the LA document in the cities collection.' . PHP_EOL);
}

?>