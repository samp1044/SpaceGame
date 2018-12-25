/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Sami
 */
public class ObjectList {
    Object[] objects;
    
    public ObjectList() {
        this.objects = new Object[0];
    }
    
    public void add(Object object) {
        if (object != null) {
            enlargeField();
            this.objects[this.objects.length - 1] = object;
        }
    }
    
    public Object getElementAt(int index) {
        Object object = null;
        
        if(index >= 0 && index < this.objects.length) {
            object = this.objects[index];
        }
        
        return object;
    }
    
    public void remove(int index) {
        if(index >= 0 && index < this.objects.length) {
            Object[] dummy = new Object[this.objects.length - 1];
            
            for (int i = index;i < this.objects.length - 1;i++) {
                this.objects[i] = this.objects[i + 1];
            }
            
            for (int i = 0;i < dummy.length;i++) {
                dummy[i] = this.objects[i];
            }
            
            this.objects = dummy;
        }
    }
    
    public void remove(Object object) {
        if (object != null) {
            for (int i = 0;i < objects.length;i++) {
                if (object.equals(objects[i])) {
                    remove(i);
                    break;
                }
            }
        }
    }
    
    public boolean contains(Object object) {
        boolean contained = false;
        
        if (object != null) {
            for (int i = 0;i < objects.length;i++) {
                if (object.equals(objects[i])) {
                    contained = true;
                    break;
                }
            }
        }
        
        return contained;
    }
    
    public int size() {
        return this.objects.length;
    }
    
    private void enlargeField() {
        Object[] dummy = new Object[this.objects.length + 1];
        
        for (int i = 0;i < this.objects.length;i++) {
            dummy[i] = this.objects[i];
        }
        
        this.objects = dummy;
    }
}
