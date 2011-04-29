<?php
/**
 * Class: ImageResizeUtil
 * Desc:  Working with content of image
 */
define('ROTATE_SUBFIX','#');

class ImageUtil
{
  var $image         = NULL;
  var $width         = 0;
  var $height        = 0;
  var $oriWidth      = 0;
  var $oriHeight     = 0;
  var $newWidth      = 0;
  var $newHeight     = 0;
  var $widthOffset   = 0;
  var $heightOffset  = 0;
  var $resizeType    = NULL;

/**
   * default value initialize.
   */
  function __initValue()
  {
  	$this->image         = NULL;
    $this->width         = 0;
    $this->height        = 0;
    $this->oriWidth      = 0;
    $this->oriHeight     = 0;
    $this->newWidth      = 0;
    $this->newHeight     = 0;
    $this->widthOffset   = 0;
    $this->heightOffset  = 0;
  }
  
  /**
   * Reize image based on its mime type
   * pjpeg / x-png add (2010-03-28) ie mime type.
   * crop procress add (2010-08-31) by yoon 
   * @param string full path to original image file
   * @param string type image/jpg, image/jpeg, image/gif, image/png
   * @param int width target width
   * @param int height target height
   * @param boolean a flag to see if we need to calculate new proportion dimension for image.
   */
  function resize($path, $type, $width, $height)
  {
  	$this->__initValue();
    $ext = preg_replace('/image\//', '', $type);
    switch ($ext)
    {
      case 'jpg':
      case 'jpeg':
      case 'pjpeg':
        $this->image = imagecreatefromjpeg($path);
        $func  = 'imagejpeg';
      break;

      case 'gif':
        $this->image = imagecreatefromgif($path);
        $func  = 'imagegif';
      break;

      case 'png':
      case 'x-png':
        $this->image = imagecreatefrompng($path);
        $func  = 'imagepng';
      break;
      default:
        $func  = 'imagepng';
    }
    
    if($this->image === FALSE){
      return false;
    }
    list($this->oriWidth, $this->oriHeight) = getimagesize($path);
    
    $this->newWidth   = $this->oriWidth; 
    $this->newHeight  = $this->oriHeight;
    
    $this->width         = $width > $this->oriWidth?$this->oriWidth:$width;
    $this->height        = $height > $this->oriHeight?$this->oriHeight:$height;
    
    $this->crop($width, $height);
    $imageResized = imagecreatetruecolor($width, $height);
    imagecopyresampled($imageResized, $this->image, 0, 0, 0, 0, $width, $height, $this->newWidth, $this->newHeight);
    
    /**
     * Capture output from buffer
     */
    ob_start();
    switch($func)
    {
      case 'imagejpeg':
        $func($imageResized,null,100); //quality 100.
        break;
      case 'imagepng':
        $func($imageResized,null,9); //quality 9.
        break;
      default:
        $func($imageResized); 
    }
    $finalImage = ob_get_contents();
    ob_end_clean();

    imagedestroy($imageResized);  // free up memory
    $this->saveFile($saveFilePath,$finalImage);
  }
  
  function saveFile($filePath, $imageResource)
  {
    $fp = @fopen($filePath,'w+');
    @fwrite($fp,$imageResource);
    @fclose($fp);
    @chmod($filePath,0777);
  }
  
  
  /**
   * thumbnail size calculate!
   * ex) 80 * 80 / 80 * 60 
   */
  function calculateSize()
  {
    if($this->oriWidth > $this->oriHeight)
    {
    	$this->newHeight    = $this->oriHeight - ($this->oriHeight % $this->height);
      $this->heightOffset = ceil(($this->oriHeight % $this->height) /2);
        
      $this->newWidth = ceil(($this->newHeight * $this->width) / $this->height);
      $this->widthOffset = ceil(($this->oriWidth - $this->newWidth) /2);
    }      
    else
    {
       $this->heightOffset = ceil(($this->oriHeight-$this->oriWidth) /2);
       $this->newHeight  = $this->newWidth;
    }
  }
 
  /**
   * image crop!!
   * important prepare calculate size.
   */
  function crop($width, $height)
  {
    $this->calculateSize();

    $widthPadding = 0;
    $heightPadding = 0;
    
    if($this->oriWidth > $this->oriHeight)
    {
      if($width > $this->newWidth)
      {
        $widthPadding = ceil(($width - $this->newWidth)/2);
        $this->newWidth = $width;
      }
      
      if($height > $this->newHeight)
      {
        $heightPadding = ceil(($height - $this->newHeight)/2);
        $this->newHeight = $height;
      }
      
      if($this->widthOffset < 0)
      {
        $widthPadding  = $this->widthOffset * -1; 
        $this->widthOffset = 0;
      }
      
      if($this->heightOffset < 0)
      {
        $heightPadding  = $this->heightOffset * -1; 
        $this->heightOffset = 0;
      }
    }
    $img = imagecreatetruecolor($this->newWidth, $this->newHeight);
    imagefilledrectangle($img, 0, 0, $this->newWidth, $this->newHeight, imagecolorallocate($img, 255, 255,255));  
    
    imagecopy($img, $this->image, $widthPadding, $heightPadding, $this->widthOffset, $this->heightOffset, $this->oriWidth, $this->oriHeight);
    imagedestroy($this->image);
    $this->image = $img; 
  }
  
  /**
   * cache article image created.
   * expected exception.
   * @param $imageId
   * @param $imageContent
   * @param $saveType
   * @return $fileFullPath
   */
  function createImageFile($imageId,$imageContent,$saveType,$ext)
  {
  	if(empty($imageContent) || empty($imageId))
  	{
  		return false;
  	}
    switch (preg_replace('/image\//', '', $ext))
    {
      case 'jpg':
      case 'jpeg':
      case 'pjpeg':
        $ext  = '.jpg';
      break;
      case 'gif':
        $ext  = '.gif';
      break;
      case 'png':
      case 'x-png':
      	$ext  = '.png';
      break;
    }
  	$saveType = empty($saveType)?'original':$saveType;
  	
  	$dirSeparate  = $this->getDirSeparate($imageId);
  	$fileDirPath = ARTICLE_IMAGE_CACHE_BASE_DIR . $saveType . DS . $dirSeparate;
  	$fileFullPath = $fileDirPath. DS .$imageId. $ext;
  	if(!is_dir($fileDirPath))
  	{
  		@mkdir($fileDirPath,0777);
  		@chmod($fileDirPath,0777);
  	}
    $fp = @fopen($fileFullPath,'w+');
    @fwrite($fp,$imageContent);
    @fclose($fp);
    return $fileFullPath;
  }
  
  /**
   * directory separate calculate
   * @param $imageId
   */
  function getDirSeparate($imageId)
  {
  	return sprintf('%02d', ($imageId % 1000) / 10);
  }
  
  /**
   * new image roatate. 
   * @param $fileFullPath
   * @param $angle
   */
  function createRotateImage($fileFullPath,$angle = 0)
  {
  	if($angle == 0 )
  	{
  		return false;
  	}
  	else 
  	{
  	  $this->__initValue();
	    $ext = preg_replace('/image\//', '', mime_content_type($fileFullPath));
	    switch ($ext)
	    {
	      case 'jpg':
	      case 'jpeg':
	      case 'pjpeg':
	        $this->image = imagecreatefromjpeg($fileFullPath);
	        $func  = 'imagejpeg';
	      break;
	
	      case 'gif':
	        $this->image = imagecreatefromgif($fileFullPath);
	        $func  = 'imagegif';
	      break;
	
	      case 'png':
	      case 'x-png':
	        $this->image = imagecreatefrompng($fileFullPath);
	        $func  = 'imagepng';
	      break;
	      default:
	      	$func  = 'imagepng';
	    }
	    $this->image = imagerotate($this->image,$angle,0);
	    ob_start();
	    switch($func)
	    {
	      case 'imagejpeg':
	        $func($this->image);
	        break;
	      case 'imagepng':
	        $func($this->image);
	        break;
	      default:
	        $func($this->image); //quality 9.
	    }
	    $finalImage = ob_get_contents();
	    ob_end_clean();
	    
	    imagedestroy($this->image);  // free up memory
	    $fp = @fopen($fileFullPath,'w+');
	    @fwrite($fp,$finalImage);
	    @fclose($fp);
  	}
  }
}

$ImageUtil = new ImageUtil();

/**
 */
if($argv[1] == 'resize'){
  $ImageUtil->resize($argv[2], $argv[3], $argv[4], $argv[5], $argv[6], $argv[7]);    
}
elseif($argv[1] == 'rotate')
{
  $ImageUtil->createRotateImage($argv[2], $argv[3]);
}