#!/bin/bash

parentdir=${PWD%/*}

size="hdpi"

if [ "$size" = "mdpi" ]; then

folder=$parentdir"/drawable-mdpi/"
dimensions="200x200"
dimensions_header="640x234"
fi

if [ $size = "hdpi" ]; then
folder=$parentdir"/drawable-hdpi/"
dimensions="240x240"
dimensions_header="800x292"
fi

if [ $size = "xhdpi" ]; then
folder=$parentdir"/drawable-xhdpi/"
dimensions="320x320"
dimensions_header="1080x394"
fi

if [ $size = "xxhdpi" ]; then
folder=$parentdir"/drawable-xxhdpi/"
dimensions="320x320"
dimensions_header="1500x548"
fi

mkdir $folder

convert art.png -resize $dimensions "$folder""art.png"
convert band.png -resize $dimensions "$folder""band.png"
convert fire.png -resize $dimensions "$folder""fire.png"
convert food.png -resize $dimensions "$folder""food.png"
convert music.png -resize $dimensions "$folder""music.png"
convert pregon.png -resize $dimensions "$folder""pregon.png"
convert sport.png -resize $dimensions "$folder""sport.png"
convert warning.png -resize $dimensions "$folder""warning.png"
convert error.png -resize $dimensions "$folder""error.png"

convert header_a_guarda.jpg -resize $dimensions_header "$folder""header_a_guarda.jpg"
convert header_art.jpg -resize $dimensions_header "$folder""header_art.jpg"
convert header_band.jpg -resize $dimensions_header "$folder""header_band.jpg"
convert header_fire.jpg -resize $dimensions_header "$folder""header_fire.jpg"
convert header_food.jpg -resize $dimensions_header "$folder""header_food.jpg"
convert header_music.jpg -resize $dimensions_header "$folder""header_music.jpg"
convert header_sport.jpg -resize $dimensions_header "$folder""header_sport.jpg"
