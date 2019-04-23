#!/bin/bash
cat play_summary.csv | sort -u > analysis/sorted
cat analysis/sorted | grep STD > analysis/std
cat analysis/sorted | grep HIT > analysis/hit
cat analysis/sorted | grep SPL > analysis/spl
cat analysis/sorted | grep DBL > analysis/dbl
cat analysis/hit | grep soft > analysis/hit_soft
cat analysis/hit | grep hard > analysis/hit_hard
cat analysis/std | grep soft > analysis/std_soft
cat analysis/std | grep hard > analysis/std_hard
