# ReaderWriterProblem

### BAU CMP3001 Term Project

In this project you will need to provide a solution to readers-writers problem in which several processes
(readers and writers) are trying to access shared variables. Obviously, if two readers access the shared
data simultaneously, no adverse effects will result, hence, they are allowed to access. However, if a
writer and some other process (either a reader or a writer) access the data simultaneously, chaos may
ensue. To ensure that these difficulties do not arise, we require that the writers have exclusive access
to the shared data while writing to the data.
The solution must guarantee that:<br>
• If a writer has begun writing process, then<br>
o No additional writer can perform write function<br>
o No reader is allowed to read<br>
• If 1 or more readers are reading, then<br>
o Other readers may read as well<br>
o No writer may perform write function until all readers have finished reading<br>

You are given Test class written in Java that use ReadWriteLock class and threads for the problem.<br>
You are expected to use Semaphore provided in the code.<br>
Two operations on the semaphore is allowed; acquire() and release() (they correspond wait and signal functions)
