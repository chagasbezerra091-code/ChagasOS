PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/init/fstab.k41s:$(TARGET_COPY_OUT_RAMDISK)/fstab.k41s \
    $(LOCAL_PATH)/init/init.k41s.rc:$(TARGET_COPY_OUT_VENDOR)/etc/init/init.k41s.rc

PRODUCT_PACKAGES += \
    charger_res_images \
    android.hardware.power@1.2-service \
    android.hardware.camera.provider@2.5-service

PRODUCT_PROPERTY_OVERRIDES += \
    ro.vendor.build.fingerprint=lge/k41s/k41s:10/QKQ1/2025:user/release-keys
