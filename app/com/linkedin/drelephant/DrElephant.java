/*
 * Copyright 2016 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.linkedin.drelephant;

import java.io.IOException;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The main class which starts Dr. Elephant
 */
public class DrElephant extends Thread {
  private ThreadPoolExecutor _threadPoolExecutor;
  private ElephantRunner _elephant_1;
  private ElephantRunner _elephant_2;

  public DrElephant() throws IOException {
          ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("dr-el-thread-%d").build();
          _threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                  new LinkedBlockingQueue<Runnable>(), factory);

    _elephant_1 = new ElephantRunner("test-realm-1");
    _elephant_2 = new ElephantRunner("test-realm-2");
  }

  @Override
  public void run() {
    _threadPoolExecutor.submit(_elephant_1);
    _threadPoolExecutor.submit(_elephant_2);
  }

  public void kill() {
    if (_elephant_1 != null) {
      _elephant_1.kill();
    }
    if (_elephant_2 != null) {
      _elephant_2.kill();
    }
  }
}
