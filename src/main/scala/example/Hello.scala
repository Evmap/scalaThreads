package example

import java.lang.Thread;

object Hello extends App {
  blockThrd
  newThrd
  runnThrd
  termThrd
  timeWaitThrd
  waitThrd


  def blockThrd() {
    try {
      val thread: Thread = new ThreadWaitingState()
      thread.start
      thread.synchronized {
        Thread.sleep(10)
        println("1: " + thread.getState())
      }
    } catch {
      case e:InterruptedException =>
        println("Error")
    }
  }

  def newThrd = {
    val thread: Thread = new Thread()
    println("2: " + thread.getState())
  }


  def runnThrd = {
    val thread: Thread = new Thread()
    thread.start
    println("3: " + thread.getState())
  }

  def termThrd = {
    val thread: Thread = new Thread()
    thread.start
    try {
      thread.interrupt
    }
    Thread.sleep(50)
    println("4: " + thread.getState())
  }

  def timeWaitThrd = {
    val thread: Thread = new ThreadWaitingState() {
      override def run() {
        try {
          this.synchronized {
            Thread.sleep(500)
          }
        } catch {
          case e:InterruptedException =>
            println("error")
        }
      }
    }
    thread.start
    Thread.sleep(50)
    println("5: " + thread.getState())
  }

  def waitThrd() {
    try {
      val thread: Thread = new ThreadWaitingState()
      thread.start
      Thread.sleep(10)
      println("6: " + thread.getState())
      thread.interrupt()
    } catch {
      case e:InterruptedException =>
        println("error")
    }
  }

}


class ThreadWaitingState extends Thread {
  override def run() {
    try {
      this.synchronized {
        wait()
      }
    } catch {
      case e:InterruptedException =>
        println("error")
    }
  }
}
