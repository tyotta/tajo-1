/**
 * 
 */
package nta.datum;

import java.nio.ByteBuffer;

import nta.datum.exception.InvalidCastException;
import nta.datum.exception.InvalidOperationException;

/**
 * @author Hyunsik Choi
 *
 */
public class IntDatum extends Datum {
  private static final int size = 4;
  
	int val;
	
	IntDatum() {
		super(DatumType.INT);
	}
	
	IntDatum(int val) {
		this();
		this.val = val;
	}
	
	public boolean asBool() {
		throw new InvalidCastException();
	}	

	@Override
	public short asShort() {	
		return (short) val;
	}

	/* (non-Javadoc)
	 * @see nta.common.datum.Datum#asInt()
	 */
	public int asInt() {		
		return val;
	}

	/* (non-Javadoc)
	 * @see nta.common.datum.Datum#asLong()
	 */
	public long asLong() {
		return val;
	}

	/* (non-Javadoc)
	 * @see nta.common.datum.Datum#asByte()
	 */
	public byte asByte() {
		throw new InvalidCastException();
	}

	/* (non-Javadoc)
	 * @see nta.common.datum.Datum#asByteArray()
	 */
	public byte[] asByteArray() {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(val);
		return bb.array();
	}

	/* (non-Javadoc)
	 * @see nta.common.datum.Datum#asFloat()
	 */
	public float asFloat() {
		return val;
	}

	/* (non-Javadoc)
	 * @see nta.common.datum.Datum#asDouble()
	 */
	public double asDouble() {
		return val;
	}

	/* (non-Javadoc)
	 * @see nta.common.datum.Datum#asChars()
	 */
	public String asChars() {
		return ""+val;
	}

  @Override
  public int size() {
    return size;
  }
  
  @Override
  public int hashCode() {
    return val;
  }
  
  public boolean equals(Object obj) {
    if (obj instanceof IntDatum) {
      IntDatum other = (IntDatum) obj;
      return val == other.val;
    }
    
    return false;
  }
  
  // Datum Comparable
  public BoolDatum equalsTo(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createBool(val == datum.asShort());
    case INT:
      return DatumFactory.createBool(val == datum.asInt());
    case LONG:
      return DatumFactory.createBool(val == datum.asLong());
    case FLOAT:
      return DatumFactory.createBool(val == datum.asFloat());
    case DOUBLE:
      return DatumFactory.createBool(val == datum.asDouble());
    default:
      throw new InvalidOperationException();
    }
  }

  public BoolDatum lessThan(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createBool(val < datum.asShort());
    case INT:
      return DatumFactory.createBool(val < datum.asInt());
    case LONG:
      return DatumFactory.createBool(val < datum.asLong());
    case FLOAT:
      return DatumFactory.createBool(val < datum.asFloat());
    case DOUBLE:
      return DatumFactory.createBool(val < datum.asDouble());
    default:
      throw new InvalidOperationException();
    }
  }
  
  public BoolDatum lessThanEqual(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createBool(val <= datum.asShort());
    case INT:
      return DatumFactory.createBool(val <= datum.asInt());
    case LONG:
      return DatumFactory.createBool(val <= datum.asLong());
    case FLOAT:
      return DatumFactory.createBool(val <= datum.asFloat());
    case DOUBLE:
      return DatumFactory.createBool(val <= datum.asDouble());
    default:
      throw new InvalidOperationException(datum.toString());
    }
  }
  
  public BoolDatum greaterThan(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createBool(val > datum.asShort());
    case INT:
      return DatumFactory.createBool(val > datum.asInt());
    case LONG:
      return DatumFactory.createBool(val > datum.asLong());
    case FLOAT:
      return DatumFactory.createBool(val > datum.asFloat());
    case DOUBLE:
      return DatumFactory.createBool(val > datum.asDouble());
    default:
      throw new InvalidOperationException(datum.type());
    }
  }
  
  public BoolDatum greaterThanEqual(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createBool(val >= datum.asShort());
    case INT:
      return DatumFactory.createBool(val >= datum.asInt());
    case LONG:
      return DatumFactory.createBool(val >= datum.asLong());
    case FLOAT:
      return DatumFactory.createBool(val >= datum.asFloat());
    case DOUBLE:
      return DatumFactory.createBool(val >= datum.asDouble());
    default:
      throw new InvalidOperationException();
    }
  }

  public Datum plus(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createInt(val + datum.asShort());
    case INT:
      return DatumFactory.createInt(val + datum.asInt());
    case LONG:
      return DatumFactory.createLong(val + datum.asLong());
    case FLOAT:
      return DatumFactory.createFloat(val + datum.asFloat());
    case DOUBLE:
      return DatumFactory.createDouble(val + datum.asDouble());
    default:
      throw new InvalidOperationException(datum.type());
    }
  }
  
  public Datum minus(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createInt(val - datum.asShort());
    case INT:
      return DatumFactory.createInt(val - datum.asInt());
    case LONG:
      return DatumFactory.createLong(val - datum.asLong());
    case FLOAT:
      return DatumFactory.createFloat(val - datum.asFloat());
    case DOUBLE:
      return DatumFactory.createDouble(val - datum.asDouble());
    default:
      throw new InvalidOperationException(datum.type());
    }
  }
  
  public Datum multiply(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createInt(val * datum.asShort());
    case INT:
      return DatumFactory.createInt(val * datum.asInt());
    case LONG:
      return DatumFactory.createLong(val * datum.asLong());
    case FLOAT:
      return DatumFactory.createFloat(val * datum.asFloat());
    case DOUBLE:
      return DatumFactory.createDouble(val * datum.asDouble());
    default:
      throw new InvalidOperationException();
    }
  }
  
  public Datum divide(Datum datum) {
    switch (datum.type()) {
    case SHORT:
      return DatumFactory.createInt(val / datum.asShort());
    case INT:
      return DatumFactory.createInt(val / datum.asInt());
    case LONG:
      return DatumFactory.createLong(val / datum.asLong());
    case FLOAT:
      return DatumFactory.createFloat(val / datum.asFloat());
    case DOUBLE:
      return DatumFactory.createDouble(val / datum.asDouble());
    default:
      throw new InvalidOperationException(datum.type());
    }
  }
}