/**
 *  OpenL Tablets,  2006
 *  https://sourceforge.net/projects/openl-tablets/ 
 */
package org.openl.rules.ui;

import org.openl.util.benchmark.BenchmarkInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Set;

/**
 * @author snshor
 * 
 */
public class WebStudio
{
    private final String workspacePath;

    static interface StudioListener extends EventListener {
        void studioReset();
    }

	ArrayList benchmarks = new ArrayList();

    List<StudioListener> listeners = new ArrayList<StudioListener>();

    int tableID = -1;

	ProjectModel model = new ProjectModel(this);

	final OpenLProjectLocator locator;

	OpenLWrapperInfo[] wrappers = null;

	WebStudioMode mode = WebStudioMode.BUSINESS1;

    private Set<String> writableProjects;

    public WebStudio() {
        this("..");
    }

    public WebStudio(String workspacePath) {
        this.workspacePath = workspacePath;
        locator = new OpenLProjectLocator(workspacePath);
    }

    public void reset() throws Exception
	{
		model.reset();
        for (StudioListener listener : listeners) listener.studioReset();
	}
	
	
	public void addBenchmark(BenchmarkInfo bi)
	{
		benchmarks.add(0, bi);
	}
	
	public void removeBenchmark(int i)
	{
		benchmarks.remove(i);
	}
	

	/**
	 * @return Returns the locator.
	 */
	public OpenLProjectLocator getLocator()
	{
		return locator;
	}

	/**
	 * @return Returns the model.
	 */
	public ProjectModel getModel()
	{
		return model;
	}

    public void setWritableProjects(Set<String> writableProjects) {
        this.writableProjects = writableProjects;
    }

    /**
	 * @return Returns the wrappers.
	 * @throws IOException
	 */
	public synchronized OpenLWrapperInfo[] getWrappers() throws IOException
	{
		if (wrappers == null)
			wrappers = locator.listOpenLProjects();
		return wrappers;
	}

	public void select(String name) throws Exception
	{
		OpenLWrapperInfo[] ww = getWrappers();
		if (name == null)
		{
			if (currentWrapper != null)
				return;

			if (ww.length > 0)
				setCurrentWrapper(ww[0]);
			return;
		}
		for (int i = 0; i < ww.length; i++)
		{
			if (ww[i].getWrapperClassName().equals(name))
			{
				setCurrentWrapper(ww[i]);
				return;
			}
		}
		if (ww.length > 0)
			setCurrentWrapper(ww[0]);
		// throw new RuntimeException("Unknown wrapper: " + name);
	}

	OpenLWrapperInfo currentWrapper;

	/**
	 * @return Returns the currentWrapper.
	 */
	public OpenLWrapperInfo getCurrentWrapper()
	{
		return currentWrapper;
	}

	/**
	 * @param wrapper
	 *          The currentWrapper to set.
	 * @throws NoSuchFieldException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 * @throws Exception
	 */
	public void setCurrentWrapper(OpenLWrapperInfo wrapper) throws Exception
	{
		if (this.currentWrapper != wrapper)
		{
			model.setWrapperInfo(wrapper);
            model.setReadOnly(!(writableProjects == null || writableProjects.contains(wrapper.getProjectInfo().getName())));
        }
		this.currentWrapper = wrapper;
        for (StudioListener listener : listeners) listener.studioReset();
    }

	/**
	 * @return Returns the tableID.
	 */
	public int getTableID()
	{
		return tableID;
	}

	/**
	 * @param tableID
	 *          The tableID to set.
	 */
	public void setTableID(int tableID)
	{
		this.tableID = tableID;
	}

	WebStudioProperties properties = new WebStudioProperties();

	public WebStudioProperties getProperties()
	{
		return this.properties;
	}

	public void setProperties(WebStudioProperties properties)
	{
		this.properties = properties;
	}

	public WebStudioMode getMode()
	{
		return this.mode;
	}

	public void setMode(WebStudioMode mode)
	{
		this.mode = mode;
	}

	// public void toggleMode() throws Exception
	// {
	// mode = mode == WebStudioMode.BUSINESS ? WebStudioMode.DEVELOPER
	// : WebStudioMode.BUSINESS;
	// model.reset();
	// }

	int businessModeIdx = 0;

	int developerModeIdx = 0;

	WebStudioMode[] businessModes = { WebStudioMode.BUSINESS1,  WebStudioMode.BUSINESS2,  WebStudioMode.BUSINESS3};

	WebStudioMode[] developerModes = { WebStudioMode.DEVELOPER };

	public void setMode(String modeStr) throws Exception
	{

		boolean sameType = modeStr.equals(mode.getType());

		if ("business".equals(modeStr))
			businessModeIdx = findMode(businessModes, businessModeIdx, sameType);
		else if ("developer".equals(modeStr))
			developerModeIdx = findMode(developerModes, developerModeIdx, sameType);
		else
			throw new RuntimeException("Invalid Mode: " + modeStr);

		model.redraw();
	}

    /**
     * @param modes
     * @param modeIdx
     * @param sameType
     * @return
     */
	private int findMode(WebStudioMode[] modes, int modeIdx, boolean sameType)
	{
		if (sameType)
			modeIdx = (modeIdx + 1) % modes.length;

		mode = modes[modeIdx];
		return modeIdx;

	}

    public void addEventListener(StudioListener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(StudioListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Returns path on local file system to openL workspace this instance of web studio works with.   
     *
	 * @return path to openL projects workspace, i.e. folder containing openL projects.
	 */
	public String getWorkspacePath()
	{
        return workspacePath;
	}


	public BenchmarkInfo[] getBenchmarks()
	{
		return (BenchmarkInfo[])this.benchmarks.toArray(new BenchmarkInfo[0]);
	}

}
