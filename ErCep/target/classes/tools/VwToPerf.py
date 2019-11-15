#!/usr/bin/python
import sys

filename = sys.argv[1]
filenameout = sys.argv[2]

f = open(filename, "r")
fout = open(filenameout, 'w')
for line in f:
  vwline = ""
  label = ""
  clas = ""
  strs = line.split(' ')
  strs = map(lambda x: x.strip(), strs)
  label = strs[-1]
  if (label=="2"):
    label = "0"
  else:
    label = label

  clas = strs[0]
  vwline = label+" "+clas

  fout.write(vwline)
  fout.write('\n')
 
f.close()
fout.close()
