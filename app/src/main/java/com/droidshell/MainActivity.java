package com.droidshell;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private String fontName = "";
	private String typeace = "";
	private String str = "";
	
	private ScrollView vscroll2;
	private LinearLayout linear3;
	private TextView textview2;
	private LinearLayout linear4;
	private Button button1;
	private TextView textview4;
	private TextView textview3;
	private EditText edittext1;
	
	private TimerTask t;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
			} else {
				initializeLogic();
			}
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll2 = findViewById(R.id.vscroll2);
		linear3 = findViewById(R.id.linear3);
		textview2 = findViewById(R.id.textview2);
		linear4 = findViewById(R.id.linear4);
		button1 = findViewById(R.id.button1);
		textview4 = findViewById(R.id.textview4);
		textview3 = findViewById(R.id.textview3);
		edittext1 = findViewById(R.id.edittext1);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().equals("dspkg")) {
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_Textview(textview2, "dspkg: 'dspkg' comando desconhecido.\n\nDigite 'dspkg --help' para saber mais.\n");
									edittext1.setText("");
									textview4.setText("");
								}
							});
						}
					};
					_timer.schedule(t, (int)(500));
				}
				if (edittext1.getText().toString().contains("dspkg install")) {
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_Textview(textview2, "\ndspkg: 'install' o comando ainda está em desenvolvimento. ");
									edittext1.setText("");
									textview4.setText("");
								}
							});
						}
					};
					_timer.schedule(t, (int)(500));
				}
				if (edittext1.getText().toString().equals("dspkg --help")) {
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_Textview(textview2, "\nDroidShell Packages (v1.0)\n\nComandos básicos:\n—   dspkg install <query> : Instala e adiciona novos pacotes\n—   dspkg update <query> : Atualiza pacotes\n—   dspkg purge <query> : Remove pacotes diante do diretório raíz (/)\n—   dspkg remove <query> : Remove pacotes, mas deixa remanescentes.\n—   dspkg clean-packages : Limpa caches de instalações recentes.\n\nBases opcionais (instalações e atualizações):\n— dspkg update --refresh : Verifica repositórios um-por-um baseados em sua fonte, e instala as atualizações recentes. ");
									edittext1.setText("");
									textview4.setText("");
								}
							});
						}
					};
					_timer.schedule(t, (int)(500));
				}
				try {
					boolean root = false;
					new RunCommandTask(root).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, edittext1.getText().toString());
					
					java.lang.System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
							java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
							@Override
							public void write(int oneByte) throws java.io.IOException {
									outputStream.write(oneByte);
									textview4.setText(new String(outputStream.toByteArray()));
							}
					}));
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_Textview(textview2, "\n".concat("$".concat(edittext1.getText().toString()).concat("\n".concat(textview4.getText().toString()))));
									edittext1.setText("");
									textview4.setText("");
								}
							});
						}
					};
					_timer.schedule(t, (int)(500));
				} catch (Exception e) {
					_Textview(textview2, "\n".concat("$".concat(edittext1.getText().toString()).concat("\n".concat(e.toString()))));
					textview4.setText("");
					edittext1.setText("");
				}
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (edittext1.getText().toString().endsWith("\n")) {
					edittext1.setText(edittext1.getText().toString().replace("\n", ""));
					button1.performClick();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		_changeActivityFont("terminal");
		button1.setVisibility(View.INVISIBLE);
		
		textview2.setTextIsSelectable(true);
		//Edited code
	}
	
	public static class CommandResult implements ShellExitCode {
		  private static String toString(List<String> lines) {
				    StringBuilder sb = new StringBuilder();
				    if (lines != null) {
						      String emptyOrNewLine = "";
						      for (String line : lines) {
								        sb.append(emptyOrNewLine).append(line);
								        emptyOrNewLine = "\n";
								      }
						    }
				    return sb.toString();
				  }
		  public final List<String> stdout;
		  public final List<String> stderr;
		  public final int exitCode;
		  public CommandResult( List<String> stdout,  List<String> stderr, int exitCode) {
				    this.stdout = stdout;
				    this.stderr = stderr;
				    this.exitCode = exitCode;
				  }
		  public boolean isSuccessful() {
				    return exitCode == SUCCESS;
				  }
		  public String getStdout() {
				    return toString(stdout);
				  }
		  public String getStderr() {
				    return toString(stderr);
				  }
		
		  @Override public String toString() {
				    return getStdout();
				  }
	}
	
	
	@SuppressWarnings("unused")
	public interface ShellExitCode {
		  int SUCCESS = 0;
		  int WATCHDOG_EXIT = -1;
		  int SHELL_DIED = -2;
		  int SHELL_EXEC_FAILED = -3;
		  int SHELL_WRONG_UID = -4;
		  int SHELL_NOT_FOUND = -5;
		  int TERMINATED = 130;
		  int COMMAND_NOT_EXECUTABLE = 126;
		  int COMMAND_NOT_FOUND = 127;
	}
	
	public class ShellNotFoundException extends java.io.IOException {
		  public ShellNotFoundException(String detailMessage) {
				    super(detailMessage);
				  }
		  public ShellNotFoundException(String message, Throwable cause) {
				    super(message, cause);
				  }
	}
	
	
	public static class StreamGobbler extends Thread {
		  public interface OnLineListener {
				    void onLine(String line);
				  }
		  private final java.io.BufferedReader reader;
		  private List<String> writer;
		  private OnLineListener listener;
		  public StreamGobbler(java.io.InputStream inputStream, List<String> outputList) {
				    reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
				    writer = outputList;
				  }
		  public StreamGobbler(java.io.InputStream inputStream, OnLineListener onLineListener) {
				    reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
				    listener = onLineListener;
				  }
		
		  @Override public void run() {
				    try {
						      String line;
						      while ((line = reader.readLine()) != null) {
								        if (writer != null) {
										          writer.add(line);
										        }
								        if (listener != null) {
										          listener.onLine(line);
										        }
								      }
						    } catch (java.io.IOException e) {
						    }
				    try {
						      reader.close();
						    } catch (java.io.IOException ignored) {
						    }
				  }
	}
	 @SuppressWarnings("unused")
	public static class Shell {
		  static final String[] AVAILABLE_TEST_COMMANDS = new String[]{"echo -BOC-", "id"};
		  public static CommandResult run(String shell, String... commands) {
				    return run(shell, commands, null);
				  }
		  public static CommandResult run(String shell, String[] commands, String[] env) {
				    List<String> stdout = Collections.synchronizedList(new ArrayList<String>());
				    List<String> stderr = Collections.synchronizedList(new ArrayList<String>());
				    int exitCode;
				    try {
						      java.lang.Process process = runWithEnv(shell, env);
						      java.io.DataOutputStream stdin = new java.io.DataOutputStream(process.getOutputStream());
						      StreamGobbler stdoutGobbler = new StreamGobbler(process.getInputStream(), stdout);
						      StreamGobbler stderrGobbler = new StreamGobbler(process.getErrorStream(), stderr);
						      stdoutGobbler.start();
						      stderrGobbler.start();
						      try {
								        for (String write : commands) {
										          stdin.write((write + "\n").getBytes("UTF-8"));
										          stdin.flush();
										        }
								        stdin.write("exit\n".getBytes("UTF-8"));
								        stdin.flush();
								      } catch (java.io.IOException e) {
								        if (e.getMessage().contains("EPIPE")) {
										        } else {
										          throw e;
										        }
								      }
						      exitCode = process.waitFor();
						      try {
								        stdin.close();
								      } catch (java.io.IOException e) {
								      }
						      stdoutGobbler.join();
						      stderrGobbler.join();
						      process.destroy();
						    } catch (InterruptedException e) {
						      exitCode = ShellExitCode.WATCHDOG_EXIT;
						    } catch (java.io.IOException e) {
						      exitCode = ShellExitCode.SHELL_WRONG_UID;
						    }
				    return new CommandResult(stdout, stderr, exitCode);
				  }
		  public static java.lang.Process runWithEnv(String command, String[] environment) throws java.io.IOException {
				    if (environment != null) {
						      Map<String, String> newEnvironment = new HashMap<>();
						      newEnvironment.putAll(System.getenv());
						      int split;
						      for (String entry : environment) {
								        if ((split = entry.indexOf("=")) >= 0) {
										          newEnvironment.put(entry.substring(0, split), entry.substring(split + 1));
										        }
								      }
						      int i = 0;
						      environment = new String[newEnvironment.size()];
						      for (Map.Entry<String, String> entry : newEnvironment.entrySet()) {
								        environment[i] = entry.getKey() + "=" + entry.getValue();
								        i++;
								      }
						    }
				    return Runtime.getRuntime().exec(command, environment);
				  }
		  public static java.lang.Process runWithEnv(String command, Map<String, String> environment) throws java.io.IOException {
				    String[] env;
				    if (environment != null && environment.size() != 0) {
						      Map<String, String> newEnvironment = new HashMap<>();
						      newEnvironment.putAll(System.getenv());
						      newEnvironment.putAll(environment);
						      int i = 0;
						      env = new String[newEnvironment.size()];
						      for (Map.Entry<String, String> entry : newEnvironment.entrySet()) {
								        env[i] = entry.getKey() + "=" + entry.getValue();
								        i++;
								      }
						    } else {
						      env = null;
						    }
				    return Runtime.getRuntime().exec(command, env);
				  }
		  static boolean parseAvailableResult(List<String> stdout, boolean checkForRoot) {
				    if (stdout == null) {
						      return false;
						    }
				    boolean echoSeen = false;
				    for (String line : stdout) {
						      if (line.contains("uid=")) {
								        return !checkForRoot || line.contains("uid=0");
								      } else if (line.contains("-BOC-")) {
								        echoSeen = true;
								      }
						    }
				    return echoSeen;
				  }
		  public static class SH {
				    private static volatile Console console;
				    public static Console getConsole() throws ShellNotFoundException {
						      if (console == null || console.isClosed()) {
								        synchronized (SH.class) {
										          if (console == null || console.isClosed()) {
												            console = new Console.Builder().useSH().setWatchdogTimeout(30).build();
												          }
										        }
								      }
						      return console;
						    }
				    public static void closeConsole() {
						      if (console != null) {
								        synchronized (SH.class) {
										          if (console != null) {
												            console.close();
												            console = null;
												          }
										        }
								      }
						    }
				    public static CommandResult run(String... commands) {
						      return Shell.run("sh", commands);
						    }
				  }
		  public static class SU {
				    private static Boolean isSELinuxEnforcing = null;
				    private static String[] suVersion = new String[]{null, null};
				    private static volatile Console console;
				    public static Console getConsole() throws ShellNotFoundException {
						      if (console == null || console.isClosed()) {
								        synchronized (SH.class) {
										          if (console == null || console.isClosed()) {
												            console = new Console.Builder().useSU().setWatchdogTimeout(30).build();
												          }
										        }
								      }
						      return console;
						    }
				    public static void closeConsole() {
						      if (console != null) {
								        synchronized (SU.class) {
										          if (console != null) {
												            console.close();
												            console = null;
												          }
										        }
								      }
						    }
				    public static CommandResult run(String... commands) {
						      try {
								        Console console = SU.getConsole();
								        return console.run(commands);
								      } catch (ShellNotFoundException e) {
								        return new CommandResult(
								            Collections.<String>emptyList(), Collections.<String>emptyList(), ShellExitCode.SHELL_NOT_FOUND);
								      }
						    }
				    public static boolean available() {
						      CommandResult result = run(Shell.AVAILABLE_TEST_COMMANDS);
						      return Shell.parseAvailableResult(result.stdout, true);
						    }
				    public static synchronized String version(boolean internal) {
						      int idx = internal ? 0 : 1;
						      if (suVersion[idx] == null) {
								        String version = null;
								        CommandResult result = Shell.run(internal ? "su -V" : "su -v", "exit");
								        for (String line : result.stdout) {
										          if (!internal) {
												            if (!line.trim().equals("")) {
														              version = line;
														              break;
														            }
												          } else {
												            try {
														              if (Integer.parseInt(line) > 0) {
																                version = line;
																                break;
																              }
														            } catch (NumberFormatException e) {
														            }
												          }
										        }
								        suVersion[idx] = version;
								      }
						      return suVersion[idx];
						    }  
				    public static boolean isSU(String shell) {
						      int pos = shell.indexOf(' ');
						      if (pos >= 0) {
								        shell = shell.substring(0, pos);
								      }
						      pos = shell.lastIndexOf('/');
						      if (pos >= 0) {
								        shell = shell.substring(pos + 1);
								      }
						      return shell.equals("su");
						    }
				
				    public static String shell(int uid, String context) {
						      String shell = "su";
						      if ((context != null) && isSELinuxEnforcing()) {
								        String display = version(false);
								        String internal = version(true);
								        if ((display != null) &&
								            (internal != null) &&
								            (display.endsWith("SUPERSU")) &&
								            (Integer.valueOf(internal) >= 190)) {
										          shell = String.format(Locale.ENGLISH, "%s --context% %s", shell, context);
										        }
								      }
						      if (uid > 0) {
								        shell = String.format(Locale.ENGLISH, "%s %d", shell, context);
								      }
						      return shell;
						    } 
				    public static String shellMountMaster() {
						      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
								        return "su --mount-master";
								      }
						      return "su";
						    }
				    public static synchronized boolean isSELinuxEnforcing() {
						      if (isSELinuxEnforcing == null) {
								        Boolean enforcing = null;
								        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
										          java.io.File f = new java.io.File("/sys/fs/selinux/enforce");
										          if (f.exists()) {
												            java.io.InputStream is = null;
												            try {
														              is = new java.io.FileInputStream("/sys/fs/selinux/enforce");
														              enforcing = (is.read() == '1');
														            } catch (Exception e) {
														            } finally {
														              if (is != null) {
																                try {
																		                  is.close();
																		                } catch (java.io.IOException ignored) {
																		                }
																              }
														            }
												          }
										          if (enforcing == null) {
												            try {
														              Class<?> SELinux = Class.forName("android.os.SELinux");
														              java.lang.reflect.Method isSELinuxEnforced = SELinux.getMethod("isSELinuxEnforced");
														              if (!isSELinuxEnforced.isAccessible()) isSELinuxEnforced.setAccessible(true);
														              enforcing = (Boolean) isSELinuxEnforced.invoke(SELinux.newInstance());
														            } catch (Exception e) {
														              enforcing = Build.VERSION.SDK_INT >= 19;
														            }
												          }
										        }
								        if (enforcing == null) {
										          enforcing = false;
										        }
								        isSELinuxEnforcing = enforcing;
								      }
						      return isSELinuxEnforcing;
						    }
				    public static synchronized void clearCachedResults() {
						      isSELinuxEnforcing = null;
						      suVersion[0] = null;
						      suVersion[1] = null;
						    }
				
				  }
		  public interface OnCommandResultListener extends ShellExitCode {
				    void onCommandResult(int commandCode, int exitCode, List<String> output);
				  }
		  public interface OnCommandLineListener extends ShellExitCode, StreamGobbler.OnLineListener {
				    void onCommandResult(int commandCode, int exitCode);
				  }
		  private static class Command {
				    private static int commandCounter = 0;
				    private final String[] commands;
				    private final int code;
				    private final OnCommandResultListener onCommandResultListener;
				    private final OnCommandLineListener onCommandLineListener;
				    private final String marker;
				    public Command(String[] commands, int code, OnCommandResultListener onCommandResultListener,
				                   OnCommandLineListener onCommandLineListener) {
						      this.commands = commands;
						      this.code = code;
						      this.onCommandResultListener = onCommandResultListener;
						      this.onCommandLineListener = onCommandLineListener;
						      this.marker = UUID.randomUUID().toString() + String.format("-%08x", ++commandCounter);
						    }
				  }
		  public static class Builder {
				
				    private Map<String, String> environment = new HashMap<>();
				    private List<Command> commands = new LinkedList<>();
				    private StreamGobbler.OnLineListener onStdoutLineListener;
				    private StreamGobbler.OnLineListener onStderrLineListener;
				    private Handler handler;
				    private boolean autoHandler = true;
				    private boolean wantStderr;
				    private String shell = "sh";
				    private int watchdogTimeout;
				    public Builder setHandler(Handler handler) {
						      this.handler = handler;
						      return this;
						    }
				    public Builder setAutoHandler(boolean autoHandler) {
						      this.autoHandler = autoHandler;
						      return this;
						    }
				    public Builder setShell(String shell) {
						      this.shell = shell;
						      return this;
						    }
				    public Builder useSH() {
						      return setShell("sh");
						    }
				    public Builder useSU() {
						      return setShell("su");
						    }
				    public Builder setWantStderr(boolean wantStderr) {
						      this.wantStderr = wantStderr;
						      return this;
						    }
				    public Builder addEnvironment(String key, String value) {
						      environment.put(key, value);
						      return this;
						    }
				    public Builder addEnvironment(Map<String, String> addEnvironment) {
						      environment.putAll(addEnvironment);
						      return this;
						    }
				    public Builder addCommand(String command) {
						      return addCommand(command, 0, null);
						    }
				    public Builder addCommand(String command, int code, OnCommandResultListener onCommandResultListener) {
						      return addCommand(new String[]{
								          command
								      }, code, onCommandResultListener);
						    }
				    public Builder addCommand(List<String> commands) {
						      return addCommand(commands, 0, null);
						    }
				    public Builder addCommand(List<String> commands, int code, OnCommandResultListener onCommandResultListener) {
						      return addCommand(commands.toArray(new String[commands.size()]), code,
						          onCommandResultListener);
						    }
				    public Builder addCommand(String[] commands) {
						      return addCommand(commands, 0, null);
						    }
				    public Builder addCommand(String[] commands, int code, OnCommandResultListener onCommandResultListener) {
						      this.commands.add(new Command(commands, code, onCommandResultListener, null));
						      return this;
						    }
				    public Builder setOnStdoutLineListener(StreamGobbler.OnLineListener onLineListener) {
						      this.onStdoutLineListener = onLineListener;
						      return this;
						    }
				    public Builder setOnStderrLineListener(StreamGobbler.OnLineListener onLineListener) {
						      this.onStderrLineListener = onLineListener;
						      return this;
						    }    
				    public Builder setWatchdogTimeout(int watchdogTimeout) {
						      this.watchdogTimeout = watchdogTimeout;
						      return this;
						    }
				    public Interactive open() {
						      return new Interactive(this, null);
						    }
				    public Interactive open(OnCommandResultListener onCommandResultListener) {
						      return new Interactive(this, onCommandResultListener);
						    }
				  }
		@SuppressWarnings("unused")
		public static class Interactive {
				    private final Handler handler;
				    private final boolean autoHandler;
				    final String shell;
				    final boolean wantSTDERR;
				    private final List<Command> commands;
				    private final Map<String, String> environment;
				    final StreamGobbler.OnLineListener onStdoutLineListener;
				    final StreamGobbler.OnLineListener onStderrLineListener;
				    private final Object idleSync = new Object();
				    private final Object callbackSync = new Object();
				    volatile String lastMarkerStdout;
				    volatile String lastMarkerStderr;
				    volatile Command command;
				    private volatile List<String> buffer;
				    private volatile boolean running;
				    private volatile boolean idle = true; // read/write only synchronized
				    private volatile boolean closed = true;
				    private volatile int callbacks;
				    private volatile int watchdogCount;
				    volatile int lastExitCode;
				    private java.lang.Process process;
				    private java.io.DataOutputStream stdin;
				    private StreamGobbler stdout;
				    private StreamGobbler stderr;
				    private java.util.concurrent.ScheduledThreadPoolExecutor watchdog;
				    int watchdogTimeout;
				    Interactive(final Builder builder, final OnCommandResultListener onCommandResultListener) {
						      autoHandler = builder.autoHandler;
						      shell = builder.shell;
						      wantSTDERR = builder.wantStderr;
						      commands = builder.commands;
						      environment = builder.environment;
						      onStdoutLineListener = builder.onStdoutLineListener;
						      onStderrLineListener = builder.onStderrLineListener;
						      watchdogTimeout = builder.watchdogTimeout;
						
						      // If a looper is available, we offload the callbacks from the gobbling threads to whichever thread created us.
						      // Would normally do this in open(), but then we could not declare handler as final
						      if ((Looper.myLooper() != null) && (builder.handler == null) && autoHandler) {
								        handler = new Handler();
								      } else {
								        handler = builder.handler;
								      }
						
						      if (onCommandResultListener != null) {
								        // Allow up to 60 seconds for SuperSU/Superuser dialog, then enable the user-specified timeout for all
								        // subsequent operations
								        watchdogTimeout = 60;
								        commands.add(0, new Command(Shell.AVAILABLE_TEST_COMMANDS, 0, new OnCommandResultListener() {
										
										          @Override public void onCommandResult(int commandCode, int exitCode, List<String> output) {
												            if ((exitCode == ShellExitCode.SUCCESS) &&
												                !Shell.parseAvailableResult(output, Shell.SU.isSU(shell))) {
														              // shell is up, but it's brain-damaged
														              exitCode = ShellExitCode.SHELL_EXEC_FAILED;
														            }
												            watchdogTimeout = builder.watchdogTimeout;
												            onCommandResultListener.onCommandResult(0, exitCode, output);
												          }
										        }, null));
								      }
						
						      if (!open() && (onCommandResultListener != null)) {
								        onCommandResultListener.onCommandResult(0, ShellExitCode.SHELL_WRONG_UID, null);
								      }
						    }
				
				    public void addCommand( String... commands) {
						      addCommand(commands, 0, (OnCommandResultListener) null);
						    }
				
				    public void addCommand( String command, int code,  OnCommandResultListener resultListener) {
						      addCommand(new String[]{command}, code, resultListener);
						    }
				
				    
				    public void addCommand( String command, int code,  OnCommandLineListener onCommandLineListener) {
						      addCommand(new String[]{command}, code, onCommandLineListener);
						    }
				
				    public void addCommand( List<String> commands) {
						      addCommand(commands, 0, (OnCommandResultListener) null);
						    }
				
				    
				    public void addCommand( List<String> commands, int code,
				                            OnCommandResultListener onCommandResultListener) {
						      addCommand(commands.toArray(new String[commands.size()]), code, onCommandResultListener);
						    }
				
				    
				    public void addCommand( List<String> commands, int code,
				                            OnCommandLineListener lineListener) {
						      addCommand(commands.toArray(new String[commands.size()]), code, lineListener);
						    }
				
				    public synchronized void addCommand( String[] commands, int code,
				                                         OnCommandResultListener resultListener) {
						      this.commands.add(new Command(commands, code, resultListener, null));
						      runNextCommand();
						    }
				
				    public synchronized void addCommand( String[] commands, int code,
				                                         OnCommandLineListener onCommandLineListener) {
						      this.commands.add(new Command(commands, code, null, onCommandLineListener));
						      runNextCommand();
						    }
				
				    private void runNextCommand() {
						      runNextCommand(true);
						    }
				
				    synchronized void handleWatchdog() {
						      final int exitCode;
						
						      if (watchdog == null)
						        return;
						      if (watchdogTimeout == 0)
						        return;
						
						      if (!isRunning()) {
								        exitCode = ShellExitCode.SHELL_DIED;
								      } else if (watchdogCount++ < watchdogTimeout) {
								        return;
								      } else {
								        exitCode = ShellExitCode.WATCHDOG_EXIT;
								      }
						
						      if (handler != null) {
								        postCallback(command, exitCode, buffer);
								      }
						
						      // prevent multiple callbacks for the same command
						      command = null;
						      buffer = null;
						      idle = true;
						
						      watchdog.shutdown();
						      watchdog = null;
						      kill();
						    }
				
				    
				    private void startWatchdog() {
						      if (watchdogTimeout == 0) {
								        return;
								      }
						      watchdogCount = 0;
						      watchdog = new java.util.concurrent.ScheduledThreadPoolExecutor(1);
						      watchdog.scheduleAtFixedRate(new Runnable() {
								
								        @Override public void run() {
										          handleWatchdog();
										        }
								      }, 1, 1, java.util.concurrent.TimeUnit.SECONDS);
						    }
				
				    
				    private void stopWatchdog() {
						      if (watchdog != null) {
								        watchdog.shutdownNow();
								        watchdog = null;
								      }
						    }
				
				    
				    private void runNextCommand(boolean notifyIdle) {
						      // must always be called from a synchronized method
						
						      boolean running = isRunning();
						      if (!running)
						        idle = true;
						
						      if (running && idle && (commands.size() > 0)) {
								        Command command = commands.get(0);
								        commands.remove(0);
								
								        buffer = null;
								        lastExitCode = 0;
								        lastMarkerStdout = null;
								        lastMarkerStderr = null;
								
								        if (command.commands.length > 0) {
										          try {
												            if (command.onCommandResultListener != null) {
														              // no reason to store the output if we don't have an OnCommandResultListener.
														              // user should catch the output with an OnLineListener in this case.
														              buffer = Collections.synchronizedList(new ArrayList<String>());
														            }
												
												            idle = false;
												            this.command = command;
												            startWatchdog();
												            for (String write : command.commands) {
														              stdin.write((write + "\n").getBytes("UTF-8"));
														            }
												            stdin.write(("echo " + command.marker + " $?\n").getBytes("UTF-8"));
												            stdin.write(("echo " + command.marker + " >&2\n").getBytes("UTF-8"));
												            stdin.flush();
												          } catch (java.io.IOException e) {
												            // stdin might have closed
												          }
										        } else {
										          runNextCommand(false);
										        }
								      } else if (!running) {
								        // our shell died for unknown reasons - abort all submissions
								        while (commands.size() > 0) {
										          postCallback(commands.remove(0), ShellExitCode.SHELL_DIED, null);
										        }
								      }
						
						      if (idle && notifyIdle) {
								        synchronized (idleSync) {
										          idleSync.notifyAll();
										        }
								      }
						    }
				
				    
				    synchronized void processMarker() {
						      if (command.marker.equals(lastMarkerStdout)
						          && (command.marker.equals(lastMarkerStderr))) {
								        postCallback(command, lastExitCode, buffer);
								        stopWatchdog();
								        command = null;
								        buffer = null;
								        idle = true;
								        runNextCommand();
								      }
						    }
				
				    synchronized void processLine(String line, StreamGobbler.OnLineListener listener) {
						      if (listener != null) {
								        if (handler != null) {
										          final String fLine = line;
										          final StreamGobbler.OnLineListener fListener = listener;
										
										          startCallback();
										          handler.post(new Runnable() {
												
												            @Override public void run() {
														              try {
																                fListener.onLine(fLine);
																              } finally {
																                endCallback();
																              }
														            }
												          });
										        } else {
										          listener.onLine(line);
										        }
								      }
						    }
				
				    
				    synchronized void addBuffer(String line) {
						      if (buffer != null) {
								        buffer.add(line);
								      }
						    }
				
				    
				    private void startCallback() {
						      synchronized (callbackSync) {
								        callbacks++;
								      }
						    }
				
				    private void postCallback(final Command fCommand, final int fExitCode, final List<String> fOutput) {
						      if (fCommand.onCommandResultListener == null && fCommand.onCommandLineListener == null) {
								        return;
								      }
						      if (handler == null/* || !handler.getLooper().getThread().isAlive()*/) {
								        if ((fCommand.onCommandResultListener != null) && (fOutput != null))
								          fCommand.onCommandResultListener.onCommandResult(fCommand.code, fExitCode, fOutput);
								        if (fCommand.onCommandLineListener != null)
								          fCommand.onCommandLineListener.onCommandResult(fCommand.code, fExitCode);
								        return;
								      }
						      startCallback();
						
						      handler.post(new Runnable() {
								
								        @Override public void run() {
										          try {
												            if ((fCommand.onCommandResultListener != null) && (fOutput != null))
												              fCommand.onCommandResultListener.onCommandResult(fCommand.code, fExitCode, fOutput);
												            if (fCommand.onCommandLineListener != null)
												              fCommand.onCommandLineListener.onCommandResult(fCommand.code, fExitCode);
												          } finally {
												            endCallback();
												          }
										        }
								      });
						    }
				
				    void endCallback() {
						      synchronized (callbackSync) {
								        callbacks--;
								        if (callbacks == 0) {
										          callbackSync.notifyAll();
										        }
								      }
						    }
				
				    
				    private synchronized boolean open() {
						      try {
								        // setup our process, retrieve stdin stream, and stdout/stderr gobblers
								        process = runWithEnv(shell, environment);
								        stdin = new java.io.DataOutputStream(process.getOutputStream());
								        stdout = new StreamGobbler(process.getInputStream(), new StreamGobbler.OnLineListener() {
										
										          @Override public void onLine(String line) {
												            synchronized (Interactive.this) {
														              if (command == null) {
																                return;
																              }
														
														              String contentPart = line;
														              String markerPart = null;
														
														              int markerIndex = line.indexOf(command.marker);
														              if (markerIndex == 0) {
																                contentPart = null;
																                markerPart = line;
																              } else if (markerIndex > 0) {
																                contentPart = line.substring(0, markerIndex);
																                markerPart = line.substring(markerIndex);
																              }
														
														              if (contentPart != null) {
																                addBuffer(contentPart);
																                processLine(contentPart, onStdoutLineListener);
																                processLine(contentPart, command.onCommandLineListener);
																              }
														
														              if (markerPart != null) {
																                try {
																		                  lastExitCode = Integer.valueOf(markerPart.substring(command.marker.length() + 1), 10);
																		                } catch (Exception e) {
																		                  // this really shouldn't happen
																		                  e.printStackTrace();
																		                }
																                lastMarkerStdout = command.marker;
																                processMarker();
																              }
														            }
												          }
										        });
								        stderr = new StreamGobbler(process.getErrorStream(), new StreamGobbler.OnLineListener() {
										
										          @Override public void onLine(String line) {
												            synchronized (Interactive.this) {
														              if (command == null) {
																                return;
																              }
														
														              String contentPart = line;
														
														              int markerIndex = line.indexOf(command.marker);
														              if (markerIndex == 0) {
																                contentPart = null;
																              } else if (markerIndex > 0) {
																                contentPart = line.substring(0, markerIndex);
																              }
														
														              if (contentPart != null) {
																                if (wantSTDERR)
																                  addBuffer(contentPart);
																                processLine(contentPart, onStderrLineListener);
																              }
														
														              if (markerIndex >= 0) {
																                lastMarkerStderr = command.marker;
																                processMarker();
																              }
														            }
												          }
										        });
								
								        // start gobbling and write our commands to the shell
								        stdout.start();
								        stderr.start();
								
								        running = true;
								        closed = false;
								
								        runNextCommand();
								
								        return true;
								      } catch (java.io.IOException e) {
								        // shell probably not found
								        return false;
								      }
						    }
				
				    
				    public void close() {
						      boolean idle = isIdle(); // idle must be checked synchronized
						
						      synchronized (this) {
								        if (!running)
								          return;
								        running = false;
								        closed = true;
								      }
						
						
						      if (!idle)
						        waitForIdle();
						
						      try {
								        try {
										          stdin.write(("exit\n").getBytes("UTF-8"));
										          stdin.flush();
										        } catch (java.io.IOException e) {
										          
										          if (e.getMessage().contains("EPIPE")) {
												            
												          } else {
												            throw e;
												          }
										        }
								
								        
								        process.waitFor();
								
								        
								        try {
										          stdin.close();
										        } catch (java.io.IOException e) {
										          
										        }
								        stdout.join();
								        stderr.join();
								        stopWatchdog();
								        process.destroy();
								      } catch (InterruptedException | java.io.IOException e) {
								        
								      }
						    }
				
				    
				    public synchronized void kill() {
						      running = false;
						      closed = true;
						
						      try {
								        stdin.close();
								      } catch (java.io.IOException e) {
								        
								      }
						      try {
								        process.destroy();
								      } catch (Exception e) {
								        
								      }
						    }
				
				    
				    public boolean isRunning() {
						      if (process == null) {
								        return false;
								      }
						      try {
								        process.exitValue();
								        return false;
								      } catch (IllegalThreadStateException e) {
								        
								      }
						      return true;
						    }
				
				    
				    public synchronized boolean isIdle() {
						      if (!isRunning()) {
								        idle = true;
								        synchronized (idleSync) {
										          idleSync.notifyAll();
										        }
								      }
						      return idle;
						    }
				
				    
				    public boolean waitForIdle() {
						      if (isRunning()) {
								        synchronized (idleSync) {
										          while (!idle) {
												            try {
														              idleSync.wait();
														            } catch (InterruptedException e) {
														              return false;
														            }
												          }
										        }
								
								        if ((handler != null) &&
								            (handler.getLooper() != null) &&
								            (handler.getLooper() != Looper.myLooper())) {
										          
										          synchronized (callbackSync) {
												            while (callbacks > 0) {
														              try {
																                callbackSync.wait();
																              } catch (InterruptedException e) {
																                return false;
																              }
														            }
												          }
										        }
								      }
						
						      return true;
						    }
				
				    
				    public boolean hasHandler() {
						      return (handler != null);
						    }
				
				  }
		
		  
		  public static class Console implements java.io.Closeable {
				
				    private final OnCloseListener onCloseListener;
				    private final Shell.Interactive shell;
				    final HandlerThread callbackThread;
				    private final boolean wantStderr;
				    List<String> stdout;
				    List<String> stderr;
				    int exitCode;
				    boolean isCommandRunning;
				    private boolean closed;
				
				    private final Shell.OnCommandResultListener commandResultListener = new Shell.OnCommandResultListener() {
						
						      @Override public void onCommandResult(int commandCode, int exitCode, List<String> stdout) {
								        Console.this.exitCode = exitCode;
								        Console.this.stdout = stdout;
								        synchronized (callbackThread) {
										          isCommandRunning = false;
										          callbackThread.notifyAll();
										        }
								      }
						    };
				
				    Console(Builder builder) throws ShellNotFoundException {
						      try {
								        onCloseListener = builder.onCloseListener;
								        wantStderr = builder.wantStderr;
								
								        callbackThread = new HandlerThread("Shell Callback");
								        callbackThread.start();
								        isCommandRunning = true;
								
								        Shell.Builder shellBuilder = new Shell.Builder();
								        shellBuilder.setShell(builder.shell);
								        shellBuilder.setHandler(new Handler(callbackThread.getLooper()));
								        shellBuilder.setWatchdogTimeout(builder.watchdogTimeout);
								        shellBuilder.addEnvironment(builder.environment);
								        shellBuilder.setWantStderr(false);
								
								        if (builder.wantStderr) {
										          shellBuilder.setOnStderrLineListener(new StreamGobbler.OnLineListener() {
												            @Override public void onLine(String line) {
														              if (stderr != null) {
																                stderr.add(line);
																              }
														            }
												          });
										        }
								
								        shell = shellBuilder.open(commandResultListener);
								        MainActivity aan = new MainActivity();
								        waitForCommandFinished();
								        if (exitCode != ShellExitCode.SUCCESS) {
										          close();
										          throw aan.new ShellNotFoundException("Access was denied or this is not a shell");
										        }
								      } catch (Exception e) {
								        MainActivity aan = new MainActivity();
								        throw aan.new ShellNotFoundException("Error opening shell '" + builder.shell + "'", e);
								      }
						    }
				
				 
				    
				    public synchronized CommandResult run(String... commands) {
						      isCommandRunning = true;
						      if (wantStderr) {
								        stderr = Collections.synchronizedList(new ArrayList<String>());
								      } else {
								        stderr = Collections.emptyList();
								      }
						      shell.addCommand(commands, 0, commandResultListener);
						      waitForCommandFinished();
						      CommandResult result = new CommandResult(stdout, stderr, exitCode);
						      stderr = null;
						      stdout = null;
						      return result;
						    }
				
				    
				    @Override public synchronized void close() {
						      try {
								        shell.close();
								      } catch (Exception ignored) {
								      }
						      synchronized (callbackThread) {
								        callbackThread.notifyAll();
								      }
						      callbackThread.interrupt();
						      callbackThread.quit();
						      closed = true;
						      if (onCloseListener != null) {
								        onCloseListener.onClosed(this);
								      }
						    }
				
				    
				    public boolean isClosed() {
						      return closed;
						    }
				
				    private void waitForCommandFinished() {
						      synchronized (callbackThread) {
								        while (isCommandRunning) {
										          try {
												            callbackThread.wait();
												          } catch (InterruptedException ignored) {
												          }
										        }
								      }
						      if (exitCode == ShellExitCode.WATCHDOG_EXIT || exitCode == ShellExitCode.SHELL_DIED) {
								        close();
								      }
						    }
				
				    
				    public interface OnCloseListener {
						
						      
						      void onClosed(Console console);
						    }
				
				    
				    public static class Builder {
						
						      Console.OnCloseListener onCloseListener;
						      Map<String, String> environment = new HashMap<>();
						      String shell = "sh";
						      boolean wantStderr = true;
						      int watchdogTimeout;
						
						      
						      public Builder setShell(String shell) {
								        this.shell = shell;
								        return this;
								      }
						
						      
						      public Builder useSH() {
								        return setShell("sh");
								      }
						
						      
						      public Builder useSU() {
								        return setShell("su");
								      }
						
						      
						      public Builder setWantStderr(boolean wantStderr) {
								        this.wantStderr = wantStderr;
								        return this;
								      }
						
						      
						      public Builder setWatchdogTimeout(int watchdogTimeout) {
								        this.watchdogTimeout = watchdogTimeout;
								        return this;
								      }
						
						      
						      public Builder addEnvironment(String key, String value) {
								        environment.put(key, value);
								        return this;
								      }
						
						      
						      public Builder addEnvironment(Map<String, String> addEnvironment) {
								        environment.putAll(addEnvironment);
								        return this;
								      }
						
						      
						      public Builder setOnCloseListener(Console.OnCloseListener onCloseListener) {
								        this.onCloseListener = onCloseListener;
								        return this;
								      }
						
						      
						      public Console build() throws ShellNotFoundException {
								        return new Console(this);
								      }
						
						    }
				
				  }
		
	}
	
	private class RunCommandTask extends AsyncTask<String, Void, CommandResult> {
		private final boolean asRoot;
		private ProgressDialog dialog;
		RunCommandTask(boolean asRoot) {
				this.asRoot = asRoot;
		}
		@Override protected void onPreExecute() {
				
		}
		@Override protected CommandResult doInBackground(String... commands) {
					if (asRoot) {
						      Shell.SU.run("su");
						      return Shell.SU.run(commands);
						    } else {
						      return Shell.SH.run(commands);
						    }
		}
		@Override protected void onPostExecute(CommandResult result) {
				if (!isFinishing()) {
						java.lang.System.out.println(resultToHtml(result));
				}
		}
		private Spanned resultToHtml(CommandResult result) {
				      StringBuilder html = new StringBuilder();
				      // exit status
				      html.append("<strong></strong> ");
				      if (result.isSuccessful()) {
						        html.append("<font color='blue'>").append("</font>");
						      } else {
						        html.append("<font color='red'>").append("</font>");
						      }
				      html.append("");
				      // stdout
				      if (result.stdout.size() > 0) {
						        html.append("<strong></strong>")
						            .append(result.getStdout().replaceAll("\n", "<br>"))
						            .append("");
						      }
				      // stderr
				      if (result.stderr.size() > 0) {
						        html.append("<p><strong>ERROR:</strong></p><p><font color='red'>")
						            .append(result.getStderr().replaceAll("\n", "<br>"))
						            .append("</font></p>");
						      }
				      return Html.fromHtml(html.toString());
				    }
	}
	{
		
		
		
		
	}
	
	public void _changeActivityFont(final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			}
			else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				}
				else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					}
					else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
	}
	
	
	public void _Textview(final TextView _t, final String _s) {
		_t.append(_s);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}