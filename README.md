# Branch-Prediction

Author: Sailasya Gangavarapu

Constructed a branch predictor simulator and used it to design branch predictors well suited to the SPECint95 benchmarks.

  
Successfully modeled four different branch predictors:
  1. Smith n-bit counter Predictor
  2. Bimodal Branch Predictor
  3. GShare Branch Predictor
  4. Hybrid Branch Predictor
  
Commands to run each branch predictor-

  1. To simulate a smith n-bit counter predictor: ./sim smith <B> <tracefile>, where B is the number of counter bits used for prediction.
  2. To simulate a bimodal predictor: ./sim bimodal <M2> <tracefile>, where M2 is the number of PC bits used to index the bimodal table.
  3. To simulate a gshare predictor: ./sim gshare <M1> <N> <tracefile>, where M1 and N are the number of PC bits and global branch history register bits used to 
     index the gshare table, respectively.
  4. To simulate a hybrid predictor: ./sim hybrid <K> <M1> <N> <M2> <tracefile>, where K is the number of PC bits used to index the chooser table, M1 
     and N are the number of PC bits and global branch history register bits used to index the gshare table (respectively), and M2 is the number of PC bits used to index      the bimodal table.
  (<tracefile> is the filename of the input trace.)
