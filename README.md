### APEX Simulator Project

A Pipeline EXample(APEX) simulator with reorder buffer,
physical register file, architectural register file, issue queue,
load store queue and functional units. Pipeline consists of 2 fetch
stages, 2 decode/dispatch stages and execution stage with several function
units with different latencies. Memory is represented as instruction and
data caches only.

![pipeline](/img/pipeline.JPG "Diagram of implemented pipeline")

Functional units that are present:
* Int FU (add, sub and logical instructions), 1 cycle latency
* Multiplication FU, 4 cycles latency, non-pipelined
* Memory FU (implements Load, Store instructions), 3 cycle latency, pipelined

**Usage:** java -jar APEX_Simulator.jar "inputfile"

inputfile - text file with assembler instructions (supported instructions
are _ADD, SUB, MOVC, MUL, AND, OR, EX-OR, LOAD, STORE, BZ, BNZ, JUMP, BAL, HALT_)

**Project:** Developed in _Java 8_ using _IntelliJ IDEA v2016.1_ project
format.

**Implementation:** Most of the project documentation can be extracted with
_javadoc_. Classes are split into according packages. Software follows MVC (Model
View Controller) ideology. Since this is a console application View is merged with
Model for simplicity purposes, but can be extended further, since all components
implement _Display_ interface. Model is enclosed in _components_ package and all its
subpackages, Controller is solely in _main_ package.

Memory(d-Cache, i-Cache) and RegisterFile(ARF, PRF, RAT) are implemented as
singletons with double-checked locking(thread safe for future extensions).
