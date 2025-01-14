/*-
 * SPDX-License-Identifier: BSD-2-Clause
 *
 * Copyright (c) 2012, Apps4Av Inc. (apps4av.com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice unmodified, this list of conditions, and the following
 *    disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.ds.avare.network;

import android.content.Context;
import android.os.AsyncTask;

import com.ds.avare.shapes.ShapeFileShape;
import com.ds.avare.storage.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * @author zkhan
 *
 */
public class ShapeFetcher {


    private ShapeTask mTask;
    private ArrayList<ShapeFileShape> mShapes;
    private Context mContext;

    /**
     *
     */
    public ShapeFetcher(Context ctx) {
        mShapes = new ArrayList<ShapeFileShape>();
        mContext = ctx;
    }

    /**
     * Parse Shape files
     */
    public void parse() {
        /*
         * Shape parsing is an expensive operation. Do not do if previous is running
         */
        if(mTask != null) {
            if(mTask.getStatus() == AsyncTask.Status.RUNNING) {
                mTask.cancel(true);
            }
        }
        
        /*
         * Start the task
         */
        mTask = new ShapeTask();
        mTask.execute();
    }
    
    /**
     * This will be non null if we have recieved shapes from internet
     * @return
     */
    public ArrayList<ShapeFileShape> getShapes() {
        return mShapes;
    }

    /**
     * @author zkhan
     *
     */
    private class ShapeTask extends AsyncTask<Object, Void, Boolean> {


        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Object... vals) {
            Thread.currentThread().setName("Shapes");

            try {
                Preferences pref = new Preferences(mContext);
                mShapes = ShapeFileShape.readFile(pref.getUserDataFolder() + File.separator + pref.getShapeFileName());
            }
            catch (Exception e) {

            }
            return true;
        }
    } 
}
