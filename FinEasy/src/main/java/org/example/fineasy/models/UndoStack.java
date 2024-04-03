package org.example.fineasy.models;

import org.example.fineasy.service.StackInterface;

import java.util.EmptyStackException;
import java.util.Vector;

public class UndoStack<T> implements StackInterface<T> {

    // Store OperationRecord Object for undo method
    private final Vector<T> stack; // Last element is the top entry in stack
    private boolean initialized = false;
    private static final int DEFAULT_INITIAL_CAPACITY = 50;
    private static final int MAX_CAPACITY = 10000;

    public UndoStack() {
        this(DEFAULT_INITIAL_CAPACITY);
    } // end default constructor

    public UndoStack(int initialCapacity) {
        checkCapacity(initialCapacity);
        stack = new Vector<T>(initialCapacity);// Size doubles as needed
        initialized = true;
    } // end constructor

    /**
     * Throws an exception if this object is not initialized.
     */
    private void checkInitialization()
    {
        if (!initialized)
            throw new SecurityException("VectorStack object is not initialized " +
                    "properly.");
    }

    /** Determine if the asked for capacity is less than the maximum.
     * @param desiredCapacity The requested capacity for the stack
     */
    private void checkCapacity(int desiredCapacity){
        if (desiredCapacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a stack " +
                    "whose capacity exceeds " +
                    "allowed maximum.");
    } // end checkCapacity

    /** Adds a new entry to the top of this stack.
     * @param newEntry an object to be added to the stack
     */
    public void push(T newEntry){
        checkInitialization();
        stack.add(newEntry);

    }

    /** Removes and returns this stack’s top entry.
     * @return  The object at the top of the stack.
     * @throws EmptyStackException if the stack is empty before the operation.
     */
    public T pop() {
        checkInitialization();
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack.remove(stack.size() - 1);
        }
    } // end pop

    /** Retrieves this stack’s top entry.
     * @return The object at the top of the stack or null if
     * @throws EmptyStackException if the stack is empty.
     */
    public T peek(){
        checkInitialization();
        if(isEmpty())
            throw new EmptyStackException();
        else
            return stack.lastElement();
    } // end peek

    /** Detects whether this stack is empty.
     * @return True if the stack is empty.
     */
    public boolean isEmpty(){
        return stack.isEmpty();
    } // end isEmpty

    /** Removes all entries from this stack */
    public void clear(){
        stack.clear();
    } // end clear

} // end VectorStack

