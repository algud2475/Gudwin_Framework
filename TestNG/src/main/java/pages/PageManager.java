package pages;

import pages.demoqa.*;

/*
Вопрос - а есть ли смысл передавать INSTANCE в BasePage, если все методы можно сделать статичными?
Почему бы сразу не вызывать PageManager.getMainPage() ? Изучить
 */

/**
 * Менеджер, возвращающий экземпляр PageObject без создания нового
 */
public class PageManager {

    private static PageManager INSTANCE = null;
    private static AlertsFrameWindowsPage alertsFrameWindowsPage;
    private static AlertsPage alertsPage;
    private static BrowserWindowsPage browserWindowsPage;
    private static ElementsPage elementsPage;
    private static FramesPage framesPage;
    private static LinksPage linksPage;
    private static MainPage mainPage;
    private static SideBar sideBar;
    private static WebTablesPage webTablesPage;

    private PageManager() {}

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public AlertsFrameWindowsPage getAlertsFrameWindowsPage() {
        if (alertsFrameWindowsPage == null) {
            alertsFrameWindowsPage = new AlertsFrameWindowsPage();
        }
        return alertsFrameWindowsPage;
    }

    public AlertsPage getAlertsPage() {
        if (alertsPage == null) {
            alertsPage = new AlertsPage();
        }
        return alertsPage;
    }

    public BrowserWindowsPage getBrowserWindowsPage() {
        if (browserWindowsPage == null) {
            browserWindowsPage = new BrowserWindowsPage();
        }
        return browserWindowsPage;
    }

    public ElementsPage getElementsPage() {
        if (elementsPage == null) {
            elementsPage = new ElementsPage();
        }
        return elementsPage;
    }

    public FramesPage getFramesPage() {
        if (framesPage == null) {
            framesPage = new FramesPage();
        }
        return framesPage;
    }

    public LinksPage getLinksPage() {
        if (linksPage == null) {
            linksPage = new LinksPage();
        }
        return linksPage;
    }

    public MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }

    public SideBar getSideBar() {
        if (sideBar == null) {
            sideBar = new SideBar();
        }
        return sideBar;
    }

    public WebTablesPage getWebTablesPage() {
        if (webTablesPage == null) {
            webTablesPage = new WebTablesPage();
        }
        return webTablesPage;
    }
}
