package nta.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import nta.catalog.Schema;
import nta.catalog.TableMeta;
import nta.catalog.TableMetaImpl;
import nta.catalog.proto.TableProtos.DataType;
import nta.catalog.proto.TableProtos.StoreType;
import nta.engine.ipc.protocolrecords.SubQueryRequest;
import nta.engine.ipc.protocolrecords.Tablet;
import nta.engine.query.SubQueryRequestImpl;
import nta.storage.Appender;
import nta.storage.StorageManager;
import nta.storage.Tuple;
import nta.storage.VTuple;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Hyunsik Choi
 *
 */
public class TestLeafServer {

  private Configuration conf;
  private NtaTestingUtility util;
  private static String TEST_PATH = "target/test-data/TestLeafServer";
  private StorageManager sm;

  @Before
  public void setUp() throws Exception {
    EngineTestingUtils.buildTestDir(TEST_PATH);
    util = new NtaTestingUtility();    
    util.startMiniZKCluster();
    util.startMiniNtaEngineCluster(2);
    conf = util.getConfiguration();
    sm = StorageManager.get(conf, TEST_PATH);
  }

  @After
  public void tearDown() throws Exception {
    util.shutdownMiniZKCluster();
    util.shutdownMiniNtaEngineCluster();
  }

  @Test
  public final void testRequestSubQuery() throws IOException {
    Schema schema = new Schema();
    schema.addColumn("name", DataType.STRING);
    schema.addColumn("id", DataType.INT);

    TableMeta meta = new TableMetaImpl();
    meta.setSchema(schema);
    meta.setStorageType(StoreType.CSV);
    
    Appender appender = sm.getTableAppender(meta, "table1");
    int tupleNum = 10000;
    Tuple tuple = null;
    for (int i = 0; i < tupleNum; i++) {
      tuple = new VTuple(2);
      tuple.put(0, "abc");
      tuple.put(1, (Integer) (i + 1));
      appender.addTuple(tuple);
    }
    appender.close();

    FileStatus status = sm.listTableFiles("table1")[0];
    Tablet[] tablets1 = new Tablet[1];
    tablets1[0] = new Tablet(status.getPath(), 0, 70000);
    LeafServer leaf1 = util.getMiniNtaEngineCluster().getLeafServer(0);

    Tablet[] tablets2 = new Tablet[1];
    tablets2[0] = new Tablet(status.getPath(), 70000, 10000);
    LeafServer leaf2 = util.getMiniNtaEngineCluster().getLeafServer(1);

    SubQueryRequest req = new SubQueryRequestImpl(new ArrayList<Tablet>(
        Arrays.asList(tablets1)), new Path(TEST_PATH, "out").toUri(),
        "select * from test where id > 5100", "test");
    leaf1.requestSubQuery(req.getProto());

    SubQueryRequest req2 = new SubQueryRequestImpl(new ArrayList<Tablet>(
        Arrays.asList(tablets2)), new Path(TEST_PATH, "out").toUri(),
        "select * from test where id > 5100", "test");
    leaf2.requestSubQuery(req2.getProto());

    leaf1.shutdown("Normally Shutdown");
  }
}
