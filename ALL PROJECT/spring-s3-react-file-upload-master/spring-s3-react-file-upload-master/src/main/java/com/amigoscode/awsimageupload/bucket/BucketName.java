package com.amigoscode.awsimageupload.bucket;

public enum BucketName {

    PROFILE_IMAGE("olawale-toheeb-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
