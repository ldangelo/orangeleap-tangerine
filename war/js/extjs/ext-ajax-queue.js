/**
 * The queue that will store all XMLHttpRequests
 */
Ext.lib.Ajax._queue = [];

/**
 * Stores the number of XMLHttpRequests being processed
 */
Ext.lib.Ajax._activeRequests = 0;

/**
 * Overwritten so pending XMLHttpRequests in the queue will be removed 
 */
Ext.lib.Ajax.abort=function(o, callback, isTimeout)
{
    if (this.isCallInProgress(o)) {
        o.conn.abort();
        window.clearInterval(this.poll[o.tId]);
        delete this.poll[o.tId];
        if (isTimeout) {
            delete this.timeout[o.tId];
        }

        this.handleTransactionResponse(o, callback, true);

        return true;
    }
    else {
        
        // check if the connection is pending and delete it
        for (var i = 0, max_i = this._queue.length; i < max_i; i++) {
            if (this._queue[i].o.tId == o.tId) {
                this._queue.splice(i, 1);
                break;
            }
        }
        
        return false;
    }
};

/**
 * Pushes the XMLHttpRequests into the queue and processes the queue afterwards.
 *
 */
Ext.lib.Ajax.asyncRequest = function(method, uri, callback, postData)
{
    var o = this.getConnectionObject();

    if (!o) {
        return null;
    }
    else {
        
        this._queue.push({
           o : o,
           method: method,
           uri: uri,
           callback: callback,
           postData : postData 
        });

        this._processQueue();
        
        return o;
    }
};

/**
 * Peeks into the queue and will process the first XMLHttpRequest found, if, and only if
 * there are not more than 2 simultaneously XMLHttpRequests already processing.
 */
Ext.lib.Ajax._processQueue = function()
{
    var to = this._queue[0];
    
    if (to && this._activeRequests < 2) {
        to = this._queue.shift();
        this._asyncRequest(to.o, to.method, to.uri, to.callback, to.postData);
    }
    
};

/**
 * Executes a XMLHttpRequest and updates the _activeRequests property to match the
 * number of concurrent ajax calls.
 */
Ext.lib.Ajax._asyncRequest = function(o, method, uri, callback, postData)
{
    this._activeRequests++;
    o.conn.open(method, uri, true);
    
    if (this.useDefaultXhrHeader) {
        if (!this.defaultHeaders['X-Requested-With']) {
            this.initHeader('X-Requested-With', this.defaultXhrHeader, true);
        }
    }
    
    if(postData && this.useDefaultHeader){
        this.initHeader('Content-Type', this.defaultPostHeader);
    }
    
     if (this.hasDefaultHeaders || this.hasHeaders) {
        this.setHeader(o);
    }
    
    this.handleReadyState(o, callback);
    o.conn.send(postData || null);    
    
};

/**
 * Called after a XMLHttpRequest finishes. Updates the number of ongoing ajax calls
 * and checks afterwards if there are still requests pending.
 */
Ext.lib.Ajax.releaseObject = function(o)
{
    o.conn = null;
    o = null;
    
    this._activeRequests--;
    this._processQueue();
};