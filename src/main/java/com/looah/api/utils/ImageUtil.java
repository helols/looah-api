package com.looah.api.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.looah.api.common.LooahAPIConfig;
import com.looah.api.models.ImageGeoInfo;

import static com.looah.api.common.LooahAPIConfig.KEY_ANGLE;
import static com.looah.api.common.LooahAPIConfig.KEY_IMAGE_GEO_INFO;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: Oct 9, 2010
 * Time: 1:49:56 PM
 * looah-api - com.looah.utils
 */
@Component
public class ImageUtil {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    LooahAPIConfig looahAPIConfig;

    /**
     * Read metadata from image file and return image info(GEO info and orientaion info)
     *
     * @param imageFile
     * @return Map<String,Object>
     */
    public Map<String, Object> readMetaData(File imageFile) {
        IImageMetadata metadata = null;
        Map<String, Object> returnMap = new HashMap<String, Object>();

        returnMap.put(KEY_IMAGE_GEO_INFO, null);
        returnMap.put(KEY_ANGLE, 0);

        try {
            metadata = Sanselan.getMetadata(imageFile);
        } catch (Exception e) {
            logger.error("getMetaData read error");
            return returnMap;
        }
        if (metadata instanceof JpegImageMetadata) {
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            TiffField orientationField = jpegMetadata.findEXIFValue(TiffTagConstants.TIFF_TAG_ORIENTATION);

            if (orientationField != null) {
                try {
                    int orientation = orientationField.getIntValue();
                    int angle = 0;
                    if (orientation != 1) {
                        if (orientation == 6) {
                            angle = -90;
                        } else if (orientation == 3) {
                            angle = 180;
                        } else if (orientation == 8) {
                            angle = 90;
                        }
                    }
                    returnMap.put(KEY_ANGLE, angle);
                } catch (ImageReadException e) {
                    logger.error("orientationField read error");
                }
            }
            /**
             * TODO : set contury and when insert geo info article id / image id set. 
             */
            ImageGeoInfo imageGeoInfo = getImageGeoInfo(jpegMetadata);

            returnMap.put(KEY_IMAGE_GEO_INFO, imageGeoInfo);
        }
        return returnMap;
    }

    private ImageGeoInfo getImageGeoInfo(JpegImageMetadata jpegMetadata) {
        TiffImageMetadata exifMetaData = jpegMetadata.getExif();
        if (exifMetaData != null) {
            try {
                TiffImageMetadata.GPSInfo gpsInfo = exifMetaData.getGPS();
                if (null != gpsInfo) {
                    ImageGeoInfo imageGeoInfo = new ImageGeoInfo();
                    imageGeoInfo.setLongitude(new BigDecimal(gpsInfo.getLongitudeAsDegreesEast()).setScale(7,BigDecimal.ROUND_DOWN).doubleValue());
                    imageGeoInfo.setLatitude(new BigDecimal(gpsInfo.getLatitudeAsDegreesNorth()).setScale(7,BigDecimal.ROUND_DOWN).doubleValue());

                    imageGeoInfo.setDegreeLongitude(gpsInfo.longitudeDegrees.intValue());
                    imageGeoInfo.setMinuteLongitude(gpsInfo.longitudeMinutes.doubleValue());
                    imageGeoInfo.setSecondLongitude(gpsInfo.longitudeSeconds.doubleValue());

                    imageGeoInfo.setDegreeLatitude(gpsInfo.latitudeDegrees.intValue());
                    imageGeoInfo.setMinuteLatitude(gpsInfo.latitudeMinutes.doubleValue());
                    imageGeoInfo.setSecondLatitude(gpsInfo.latitudeSeconds.doubleValue());

                    imageGeoInfo.setLongitudeRef(gpsInfo.longitudeRef);
                    imageGeoInfo.setLatitudeRef(gpsInfo.latitudeRef);
                    imageGeoInfo.setEmpry(false);
                    return imageGeoInfo;
                }
            } catch (ImageReadException e) {
                logger.error("exifMeta");
            }
        }
        return null;
    }
    
    public Map<String, Object> saveImageFile(String filePath){
        String[] rotateOption = {"php",
                "-f",
                looahAPIConfig.IMAGE_UTIL_LOCATION,
                "rotate",
                null,  // 4 filePath
                null}; // 5: angle
        Map<String, Object> metaData = this.readMetaData(new File(filePath));
        metaData.put("isRotate",false);
        if (new Integer(metaData.get(KEY_ANGLE).toString()) != 0) {
            rotateOption[4] = filePath;
            rotateOption[5] = metaData.get(KEY_ANGLE).toString();
            this.runShell(rotateOption);
            metaData.put("isRotate",true);
        }
        return metaData;
    }

    public int runShell(String... option) {
        int exitCode = -1;
        try {
            Process ps = new ProcessBuilder(option).start();
            ps.waitFor();
            exitCode = ps.exitValue();
            ps.destroy();
        } catch (Exception e) {
            logger.error("runShell");
        }
        return exitCode;
    }
}
