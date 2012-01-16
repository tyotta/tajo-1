package nta.engine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

import nta.conf.NtaConf;
import nta.engine.exception.NTAQueryException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.ipc.RPC;

public class NtaEngineClient {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		NtaConf conf = new NtaConf();
		FileSystem fs = FileSystem.get(conf);
		NtaEngineInterface cli = 
				(NtaEngineInterface) RPC.getProxy(NtaEngineInterface.class, 0l, 
						new InetSocketAddress("127.0.1.1",9001), conf);

		Scanner in = new Scanner(System.in);
		String query = null;
		System.out.print("nta> ");
		while((query = in.nextLine()).compareTo("exit") != 0) {
			try {
				//			System.out.println(cli.executeQueryC(query));
				fs.delete(new Path("/out"), true);
				String res = cli.executeQueryC(query);
				if (!res.equals("")) {
					System.out.println(res);
				} else {
					FileStatus[] outs = fs.listStatus(new Path("/out"));
					for (FileStatus out : outs) {
						FSDataInputStream ins = fs.open(out.getPath());
						while (ins.available() > 0) {
							System.out.println(ins.readLine());
						}
					}
				}
			} catch (NTAQueryException nqe) {
				System.err.println(nqe.getMessage());
			}
			System.out.print("nta> ");
		}
	}
}