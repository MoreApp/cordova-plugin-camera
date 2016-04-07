#pragma version(1)
#pragma rs java_package_name(org.apache.cordova.camera)

rs_allocation inImage;
int inWidth;
int inHeight;

uchar4 __attribute__ ((kernel)) rotate_90_counterclockwise (uchar4 v_in, uint32_t x, uint32_t y) {
    uint32_t inX = y;
    uint32_t inY = inHeight - 1 - x;

    const uchar4 *v_out = rsGetElementAt(inImage, inX, inY);
    return *v_out;
}

uchar4 __attribute__ ((kernel)) rotate_180 (uchar4 v_in, uint32_t x, uint32_t y) {
    uint32_t inX  = inWidth - 1 - x;
    uint32_t inY = inHeight - 1 - y;
    const uchar4 *v_out = rsGetElementAt(inImage, inX, inY);
    return *v_out;
}

uchar4 __attribute__ ((kernel)) rotate_270_counterclockwise (uchar4 v_in, uint32_t x, uint32_t y) {
    uint32_t inX  = inWidth - 1 - y;
    uint32_t inY = x;
    const uchar4 *v_out = rsGetElementAt(inImage, inX, inY);
    return *v_out;
}