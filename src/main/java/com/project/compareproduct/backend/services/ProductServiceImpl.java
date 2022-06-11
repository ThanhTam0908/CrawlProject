package com.project.compareproduct.backend.services;

import com.project.compareproduct.backend.repository.*;

import java.util.*;

import com.project.compareproduct.backend.exception.ResourceNotFoundException;
import com.project.compareproduct.backend.model.*;
import com.project.compareproduct.backend.payload.BatteryResponse;
import com.project.compareproduct.backend.payload.CPUResponse;
import com.project.compareproduct.backend.payload.GraphicResponse;
import com.project.compareproduct.backend.payload.LaptopResponse;
import com.project.compareproduct.backend.payload.ProductResponse;
import com.project.compareproduct.backend.payload.RamResponse;
import com.project.compareproduct.backend.payload.ScreenResponse;
import com.project.compareproduct.backend.payload.StorageResponse;
import com.project.compareproduct.backend.payload.UrlRequest;
import com.project.compareproduct.backend.payload.productUpdateRequest;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.lang.InterruptedException;

import com.project.compareproduct.backend.utils.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@Service
public class ProductServiceImpl implements ProductService {
    private HashSet<String> links;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CPURepository cpuRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private BatteryRepository batteryRepository;

    @Autowired
    private GraphicCardRepository graphicCardRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private LaptopDMXRepository laptopDMXRepository;

    @Autowired
    private LaptopFPTRepository laptopFPTRepository;

    @Autowired
    private LaptopAmzRepository laptopAmzRepository;

    public static HashSet<String> uniqueURL = new HashSet<String>();
    public static HashSet<String> specificURL = new HashSet<String>();
    public static String my_site;

    public ProductServiceImpl() {
        links = new HashSet<String>();
    }

    @Override
    public Product crawlProduct(UrlRequest url) {
        Product prod = new Product();
        CPU cpu = new CPU();
        GraphicCard graphic = new GraphicCard();
        Screen screen = new Screen();
        Battery battery = new Battery();
        Ram ram = new Ram();
        Storage storage = new Storage();
        BatteryResponse batteryres = new BatteryResponse();
        ScreenResponse screenres = new ScreenResponse();
        CPUResponse cpures = new CPUResponse();
        ProductResponse prodres = new ProductResponse();
        GraphicResponse grares = new GraphicResponse();
        RamResponse ramres = new RamResponse();
        StorageResponse storageres = new StorageResponse();
        Storage updatedStorage = new Storage();
        Product updatedProduct = new Product();
        Screen updatedScreen = new Screen();
        GraphicCard updatedgraphic = new GraphicCard();
        Battery updatedBattery = new Battery();
        Ram updatedRam = new Ram();
        CPU updatedCpu = new CPU();
        // if (!links.contains(url.getUrl())) {
        try {
            // if (links.add(url.getUrl())) {
            // System.out.println("url " + url.getUrl());
            // }

            Document document = Jsoup.connect(url.getUrl()).get();

            Elements imageList = document.select("img[src]");

            for (Element image : imageList) {
                String imgSrc = "https://nanoreview.net" + image.attr("src");
                if (imgSrc.contains("/common/images/laptop")) {
                    prodres.setImage(imgSrc);
                }
            }

            Elements cardOnPage = document.select("div.card");

            prodres.setName(document.select("div.card-head").first().text());
            for (Element row : cardOnPage) {

                if (row.select("div.card-head").text().equals("")) {
                    continue;
                } else {
                    final String componentName = row.select("div.card-head").text();
                    System.out.println(componentName);
                    for (Element element1 : row.select("div.specs-table.flex.card-block.bb-light")) {
                        final String inputElements = element1.getElementsByTag("label").first().text();
                        final String inputName = element1.select("div.cell-h").text();

                        String laptopDetail = inputElements.replaceAll("[^0-9,\\.]", ",");
                        String[] item = laptopDetail.split(",");
                        for (int i = 0; i < item.length; i++) {
                            try {
                                Double.parseDouble(item[i]);
                                System.out.println(item[i] + " " + i);
                            } catch (NumberFormatException e) {
                            }
                        }
                        switch (inputName) {
                            case "CPU name":
                                cpures.setName(inputElements);
                                break;
                            case "GPU name":
                                grares.setName(inputElements);
                                break;
                            case "Capacity":
                                batteryres.setCapacity(Double.parseDouble(item[0]));
                                break;
                            case "RAM size":
                                ramres.setSize(Long.parseLong(item[0]));
                                break;
                            case "Storage size":
                                storageres.setSize(Long.parseLong(item[0]));
                        }
                        if (componentName.equals("Display")) {
                            screenres.setResolution(item[0] + "x" + item[3]);
                        }
                        System.out.println(inputName + " " + inputElements);
                    }
                    for (Element element : row.select("table.specs-table tr")) {
                        final String componentDetailName = element.select("td:nth-of-type(1)").text();
                        final String componentDetail = element.select("td:nth-of-type(2)").text();
                        final String laptopDetail;
                        laptopDetail = componentDetail.replaceAll("[^0-9,\\.]", ",");
                        String[] item = laptopDetail.split(",");
                        for (int i = 0; i < item.length; i++) {
                            try {
                                Double.parseDouble(item[i]);
                                System.out.println(item[i] + " " + i);
                            } catch (NumberFormatException e) {
                            }
                        }
                        switch (componentDetailName) {
                            case "Colors":
                                prodres.setColors(componentDetail);
                                break;
                            case "Weight":
                                prodres.setWeight(Double.parseDouble(item[0]));
                                break;
                            case "Area":
                                prodres.setArea(Double.parseDouble(item[0]));
                                break;
                            case "Total slots":
                                if (componentName.equals("Storage")) {
                                    prodres.setStorageSlot(Long.parseLong(item[0]));
                                } else if (componentName.equals("RAM")) {
                                    prodres.setMaxRamSlot(Long.parseLong(item[0]));
                                }
                                break;
                            case "Screen-to-body ratio":
                                prodres.setScreentobodyRatio(Double.parseDouble(item[1]));
                                break;
                            case "Base frequency":
                                cpures.setBaseFrequency(Double.parseDouble(item[0]));
                                break;
                            case "Turbo frequency":
                                cpures.setTurboFrequency(Double.parseDouble(item[0]));
                                break;
                            case "Cores":
                                cpures.setCores(Long.parseLong(item[0]));
                                break;
                            case "Threads":
                                cpures.setThreads(Long.parseLong(item[0]));
                                break;
                            case "L3 Cache":
                                cpures.setL3cache(Long.parseLong(item[0]));
                                break;
                            case "Integrated GPU":
                                cpures.setIntegratedGpu(componentDetail);
                                break;
                            case "Fabrication process":
                                if (componentName.equals("CPU")) {
                                    cpures.setFabricationProcess(item[0]);
                                } else if (componentName.equals("Graphics Card")) {
                                    grares.setFabricationProcess(Long.parseLong(item[0]));
                                }
                                break;
                            case "FLOPS":
                                grares.setFlops(Double.parseDouble(item[0]));
                                break;
                            case "Memory size":
                                grares.setMemorySize(componentDetail);
                                break;
                            case "Memory bus":
                                grares.setMemoryBus(Long.parseLong(item[0]));
                                break;
                            case "Memory speed":
                                if (!item[0].isEmpty()) {
                                    grares.setMemorySpeed(Double.parseDouble(item[0]));
                                } else {
                                    grares.setMemorySpeed(Double.parseDouble(item[1]));
                                }
                                break;
                            case "Memory type":
                                grares.setMemoryType(componentDetail);
                                break;
                            case "DirectX support":
                                grares.setDirectxSupport(Long.parseLong(item[0]));
                                break;
                            case "Type":
                                if (componentName.equals("Display")) {
                                    screenres.setType(componentDetail);
                                } else if (componentName.equals("Graphics Card")) {
                                    grares.setType(componentDetail);
                                } else if (componentName.equals("RAM")) {
                                    ramres.setType(componentDetail);
                                }
                                break;
                            case "TGP":
                                grares.setTgp(Long.parseLong(item[0]));
                                break;
                            case "Size":
                                screenres.setSize(Double.parseDouble(item[0]));
                                break;
                            case "Refresh rate":
                                screenres.setRefreshRate(Long.parseLong(item[0]));
                                break;
                            case "Aspect ratio":
                                screenres.setAspectRatio(componentDetail);
                                break;
                            case "PPI":
                                screenres.setPpi(Long.parseLong(item[0]));
                                break;
                            case "HDR support":
                                screenres.setHdrSupport(componentDetail);
                                break;
                            case "Touchscreen":
                                screenres.setTouchScreen(componentDetail);
                                break;
                            case "Charge power":
                                batteryres.setChargePower(Long.parseLong(item[0]));
                                break;
                            case "Battery type":
                                batteryres.setType(componentDetail);
                                break;
                            case "Replaceable":
                                batteryres.setReplaceable(componentDetail);
                                break;
                            case "Fast charging":
                                batteryres.setFastCharge(componentDetail);
                                break;
                            case "USB Power Delivery":
                                batteryres.setUsbPowerDelivery(componentDetail);
                                break;
                            case "Channels":
                                if (componentName.equals("RAM")) {
                                    ramres.setChannels(item[0] + "x" + item[1]);
                                } else if (componentName.equals("Storage")) {
                                    storageres.setChannels(item[0] + "x" + item[1]);
                                }
                                break;
                            case "Clock":
                                ramres.setClock(Long.parseLong(item[0]));
                                break;
                            case "Upgradable":
                                ramres.setUpgradable(componentDetail);
                                break;
                            case "Bus":
                                storageres.setBus(componentDetail);
                                break;
                            case "Storage type":
                                storageres.setType(componentDetail);
                                break;
                            case "NVMe":
                                storageres.setNVMe(componentDetail);
                                break;
                        }
                        System.out.println(componentDetailName + " " + componentDetail);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("For '" + url.getUrl() + "': " + e.getMessage());
        }
        // }
        if (storageRepository.findByChannels(storageres.getChannels()) != null) {
            storage = storageRepository.findByChannels(storageres.getChannels());
            storage.setNVMe(storageres.getNVMe());
            storage.setBus(storageres.getBus());
            storage.setChannels(storageres.getChannels());
            storage.setSize(storageres.getSize());
            storage.setType(storageres.getType());
            updatedStorage = storageRepository.save(storage);
        } else {
            storage.setNVMe(storageres.getNVMe());
            storage.setBus(storageres.getBus());
            storage.setChannels(storageres.getChannels());
            storage.setSize(storageres.getSize());
            storage.setType(storageres.getType());
            updatedStorage = storageRepository.save(storage);
        }
        if (ramRepository.findByChannels(ramres.getChannels()) != null) {
            ram = ramRepository.findByChannels(ramres.getChannels());
            ram.setChannels(ramres.getChannels());
            ram.setClock(ramres.getClock());
            ram.setSize(ramres.getSize());
            ram.setType(ramres.getType());
            ram.setUpgradable(ramres.getUpgradable());
            updatedRam = ramRepository.save(ram);
        } else {
            ram.setChannels(ramres.getChannels());
            ram.setClock(ramres.getClock());
            ram.setSize(ramres.getSize());
            ram.setType(ramres.getType());
            ram.setUpgradable(ramres.getUpgradable());
            updatedRam = ramRepository.save(ram);
        }
        if (batteryRepository.findByCapacity(batteryres.getCapacity()) != null) {
            battery = batteryRepository.findByCapacity(batteryres.getCapacity());
            battery.setCapacity(batteryres.getCapacity());
            battery.setChargePower(batteryres.getChargePower());
            battery.setFastCharge(batteryres.getFastCharge());
            battery.setFullChargeTime(batteryres.getFullChargeTime());
            battery.setReplaceable(batteryres.getReplaceable());
            battery.setType(batteryres.getType());
            battery.setUsbPowerDelivery(batteryres.getUsbPowerDelivery());
            updatedBattery = batteryRepository.save(battery);
        } else {
            battery.setCapacity(batteryres.getCapacity());
            battery.setChargePower(batteryres.getChargePower());
            battery.setFastCharge(batteryres.getFastCharge());
            battery.setFullChargeTime(batteryres.getFullChargeTime());
            battery.setReplaceable(batteryres.getReplaceable());
            battery.setType(batteryres.getType());
            battery.setUsbPowerDelivery(batteryres.getUsbPowerDelivery());
            updatedBattery = batteryRepository.save(battery);
        }
        if (screenRepository.findByPpi(screenres.getPpi()) != null) {
            screen = screenRepository.findByPpi(screenres.getPpi());
            screen.setAspectRatio(screenres.getAspectRatio());
            screen.setSize(screenres.getSize());
            screen.setHdrSupport(screenres.getHdrSupport());
            screen.setPpi(screenres.getPpi());
            screen.setRefreshRate(screenres.getRefreshRate());
            screen.setResolution(screenres.getResolution());
            screen.setTouchScreen(screenres.getTouchScreen());
            screen.setType(screenres.getType());
            updatedScreen = screenRepository.save(screen);
        } else {
            screen.setAspectRatio(screenres.getAspectRatio());
            screen.setSize(screenres.getSize());
            screen.setHdrSupport(screenres.getHdrSupport());
            screen.setPpi(screenres.getPpi());
            screen.setRefreshRate(screenres.getRefreshRate());
            screen.setResolution(screenres.getResolution());
            screen.setTouchScreen(screenres.getTouchScreen());
            screen.setType(screenres.getType());
            updatedScreen = screenRepository.save(screen);
        }
        if (graphicCardRepository.findByName(grares.getName()) != null) {
            graphic = graphicCardRepository.findByName(grares.getName());
            graphic.setName(grares.getName());
            graphic.setTgp(grares.getTgp());
            graphic.setType(grares.getType());
            graphic.setFabricationProcess(grares.getFabricationProcess());
            graphic.setFlops(grares.getFlops());
            graphic.setMemorySize(grares.getMemorySize());
            graphic.setMemorySpeed(grares.getMemorySpeed());
            graphic.setMemoryType(grares.getMemoryType());
            graphic.setMemoryBus(grares.getMemoryBus());
            graphic.setDirectxSupport(grares.getDirectxSupport());
            updatedgraphic = graphicCardRepository.save(graphic);
        } else {
            graphic.setName(grares.getName());
            graphic.setTgp(grares.getTgp());
            graphic.setType(grares.getType());
            graphic.setFabricationProcess(grares.getFabricationProcess());
            graphic.setFlops(grares.getFlops());
            graphic.setMemorySize(grares.getMemorySize());
            graphic.setMemorySpeed(grares.getMemorySpeed());
            graphic.setMemoryType(grares.getMemoryType());
            graphic.setMemoryBus(grares.getMemoryBus());
            graphic.setDirectxSupport(grares.getDirectxSupport());
            updatedgraphic = graphicCardRepository.save(graphic);
        }
        if (cpuRepository.findByName(cpures.getName()) != null) {
            cpu = cpuRepository.findByName(cpures.getName());
            cpu.setName(cpures.getName());
            cpu.setCores(cpures.getCores());
            cpu.setThreads(cpures.getThreads());
            cpu.setL3Cache(cpures.getL3cache());
            cpu.setIntegratedGpu(cpures.getIntegratedGpu());
            cpu.setBaseFrequency(cpures.getBaseFrequency());
            cpu.setTurboFrequency(cpures.getTurboFrequency());
            cpu.setFabricationProcess(cpures.getFabricationProcess());
            updatedCpu = cpuRepository.save(cpu);
        } else {
            cpu.setName(cpures.getName());
            cpu.setCores(cpures.getCores());
            cpu.setThreads(cpures.getThreads());
            cpu.setL3Cache(cpures.getL3cache());
            cpu.setIntegratedGpu(cpures.getIntegratedGpu());
            cpu.setBaseFrequency(cpures.getBaseFrequency());
            cpu.setTurboFrequency(cpures.getTurboFrequency());
            cpu.setFabricationProcess(cpures.getFabricationProcess());
            updatedCpu = cpuRepository.save(cpu);
        }
        System.out.println("prod name: " + prodres.getName());
        if (productRepository.findByName(prodres.getName()) != null) {
            prod = productRepository.findByName(prodres.getName());
            prod.setImage(prodres.getImage());
            prod.setName(prodres.getName());
            prod.setArea(prodres.getArea());
            prod.setColors(prodres.getColors());
            prod.setScreentobodyRatio(prodres.getScreentobodyRatio());
            prod.setWeight(prodres.getWeight());
            prod.setMaxRamSlot(prodres.getMaxRamSlot());
            prod.setStorageSlot(prodres.getStorageSlot());
            prod.setCpu(updatedCpu);
            prod.setGraphic(updatedgraphic);
            prod.setScreen(updatedScreen);
            prod.setBattery(updatedBattery);
            prod.setRam(updatedRam);
            prod.setStorage(updatedStorage);
            updatedProduct = productRepository.save(prod);
        } else {
            prod.setImage(prodres.getImage());
            prod.setName(prodres.getName());
            prod.setArea(prodres.getArea());
            prod.setColors(prodres.getColors());
            prod.setScreentobodyRatio(prodres.getScreentobodyRatio());
            prod.setWeight(prodres.getWeight());
            prod.setMaxRamSlot(prodres.getMaxRamSlot());
            prod.setStorageSlot(prodres.getStorageSlot());
            prod.setCpu(updatedCpu);
            prod.setGraphic(updatedgraphic);
            prod.setScreen(updatedScreen);
            prod.setBattery(updatedBattery);
            prod.setRam(updatedRam);
            prod.setStorage(updatedStorage);
            updatedProduct = productRepository.save(prod);
        }
        return updatedProduct;
    }

    @Override
    public LaptopDMX crawlLaptopDMX(UrlRequest url) {
        LaptopDMX laptop = new LaptopDMX();
        LaptopResponse laptopres = new LaptopResponse();
        LaptopDMX updatedLaptop = new LaptopDMX();
        try {
            Document document = Jsoup.connect(url.getUrl()).get();
            String laptopName = document.getElementsByTag("h1").first().text();
            laptopName = laptopName.replaceFirst("Laptop ", "");
            Elements laptopAdvantage = document.select("ul.policy__list");
            String laptopAdvan = "";
            for (Element row : laptopAdvantage) {
                final String advantage = row.text();
                laptopAdvan = laptop + ". " + advantage;
            }
            laptopres.setAdvantage(laptopAdvan);
            laptopres.setName(laptopName);

            String price = document.select("p.box-price-present").first().text();
            String priceReplace = price.replaceAll("[^0-9,\\.]", ",");
            String[] priceSplit = priceReplace.split(",");
            laptopres.setPrice(Long.parseLong(priceSplit[0].replace(".", "")));
            // System.out.println(Long.parseLong(priceSplit[1]));
            Elements cardOnPage = document.select("div.parameter");
            for (Element row : cardOnPage) {
                if (row.select("li p.lileft").text().equals("")) {
                    continue;
                } else {
                    for (Element element : row.getElementsByTag("li")) {
                        final String componentDetailName = element.select("p.lileft").text();
                        final String componentDetail = element.getElementsByTag("span").text();
                        final String laptopDetail;
                        final String[] laptopDetailSplit;
                        laptopDetailSplit = componentDetail.split(" ");
                        laptopDetail = componentDetail.replaceAll("[^0-9,\\.]", ",");
                        String[] item = laptopDetail.split(",");
                        for (int i = 0; i < item.length; i++) {
                            try {
                                Double.parseDouble(item[i]);
                                System.out.println(item[i] + " " + i);
                            } catch (NumberFormatException e) {
                            }
                        }
                        System.out.println(componentDetailName + " " + componentDetail);
                        switch (componentDetailName) {
                            case "CPU:":
                                laptopres.setCpu(componentDetail);
                                break;
                            case "RAM:":
                                laptopres.setRam(Long.parseLong(item[0]));
                                break;
                            case "Ổ cứng:":
                                laptopres.setStorageCapacity(Long.parseLong(item[0]));
                                laptopres.setStorageType(laptopDetailSplit[2]);
                                break;
                            case "Card màn hình:":
                                laptopres.setGraphic(componentDetail);
                                break;
                            case "Màn hình:":
                                laptopres.setScreen(componentDetail);
                                break;
                            case "Hệ điều hành:":
                                laptopres.setOs(componentDetail);
                                break;
                            case "Kích thước, trọng lượng:":
                                laptopres.setArea(Double.parseDouble(item[4]) * Double.parseDouble(item[15])
                                        * Double.parseDouble(item[25]));
                                laptopres.setLength(Double.parseDouble(item[4]));
                                laptopres.setWidth(Double.parseDouble(item[15]));
                                laptopres.setThick(Double.parseDouble(item[25]));
                                System.out.println(
                                        "kich thuoc:                            "
                                                + Double.parseDouble(item[4]) * Double.parseDouble(item[15])
                                                        * Double.parseDouble(item[25]));
                                if (!item[36].equals("")) {
                                    laptopres.setWeight(Double.parseDouble(item[36]));
                                } else if (!item[44].equals("")) {
                                    laptopres.setWeight(Double.parseDouble(item[44]));
                                } else if (!item[49].equals("")) {
                                    laptopres.setWeight(Double.parseDouble(item[49]));
                                }
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("For '" + url.getUrl() + "': " + e.getMessage());
        }
        System.out.println("cpu " + laptopres.getCpu() + " storage " + laptopres.getStorageType());
        if (laptopDMXRepository.findByName(laptopres.getName()) != null) {
            laptop = laptopDMXRepository.findByName(laptopres.getName());
            laptop.setAdvantage(laptopres.getAdvantage());
            laptop.setThick(laptopres.getThick());
            laptop.setLength(laptopres.getLength());
            laptop.setWidth(laptopres.getWidth());
            laptop.setArea(laptopres.getArea());
            laptop.setCpu(laptopres.getCpu());
            laptop.setGraphic(laptopres.getGraphic());
            laptop.setName(laptopres.getName());
            laptop.setOs(laptopres.getOs());
            laptop.setPrice(laptopres.getPrice());
            laptop.setRam(laptopres.getRam());
            laptop.setScreen(laptopres.getScreen());
            laptop.setStorageCapacity(laptopres.getStorageCapacity());
            laptop.setStorageType(laptopres.getStorageType());
            laptop.setWeight(laptopres.getWeight());
            updatedLaptop = laptopDMXRepository.save(laptop);
        } else {
            laptop.setAdvantage(laptopres.getAdvantage());
            laptop.setArea(laptopres.getArea());
            laptop.setCpu(laptopres.getCpu());
            laptop.setGraphic(laptopres.getGraphic());
            laptop.setName(laptopres.getName());
            laptop.setOs(laptopres.getOs());
            laptop.setPrice(laptopres.getPrice());
            laptop.setRam(laptopres.getRam());
            laptop.setScreen(laptopres.getScreen());
            laptop.setStorageCapacity(laptopres.getStorageCapacity());
            laptop.setStorageType(laptopres.getStorageType());
            laptop.setWeight(laptopres.getWeight());
            updatedLaptop = laptopDMXRepository.save(laptop);
        }
        return updatedLaptop;
    }

    @Override
    public List<Product> crawlAllProduct(UrlRequest url) {
        my_site = "nanoreview.net/en";
        List<Product> prodList = new ArrayList<Product>();
        try {
            Document doc = Jsoup.connect(url.getUrl()).userAgent("Mozilla").get();
            Elements links = doc.select("a[href]");

            if (links.isEmpty()) {
                return null;
            }

            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
                boolean add = uniqueURL.add(this_url);
                UrlRequest urlReq = new UrlRequest();
                urlReq.setUrl(this_url);
                if (add && this_url.contains(my_site)) {
                    // System.out.println(this_url);

                    boolean addspecific = specificURL.add(this_url);
                    if (addspecific && this_url.contains("nanoreview.net/en/laptop/")) {

                        System.out.println(this_url);
                        prodList.add(crawlProduct(urlReq));
                        // get_links(this_url);
                    }
                    crawlAllProduct(urlReq);

                }
            });

        } catch (IOException ex) {

        }
        return prodList;

    }

    @Override
    public LaptopFPT crawlLaptopFPT(UrlRequest url) {
        LaptopFPT laptop = new LaptopFPT();
        LaptopResponse laptopres = new LaptopResponse();
        LaptopFPT updatedLaptop = new LaptopFPT();
        System.setProperty("webdriver.chrome.driver", AppConstants.CHROME_DRIVER);
        WebDriver driver = new ChromeDriver();
        driver.get(url.getUrl());
        String laptopName = driver.findElement(By.xpath("//h1[@class='st-name']")).getText();
        laptopres.setName(laptopName);
        String price = driver.findElement(By.xpath("//div[@class='st-price-main']")).getText();

        String priceReplace = price.replaceAll("[^0-9,\\.]", ",");
        String[] priceSplit = priceReplace.split(",");
        laptopres.setPrice(Long.parseLong(priceSplit[0].replace(".", "")));

        List<WebElement> laptopAdvan = driver
                .findElements(By.xpath("//ul[@class='st-boxPromo__list st-boxPromo__list--more']/li/div/span"));
        String advantage = "";
        for (WebElement element : laptopAdvan) {
            String advan = element.getText().trim();
            advantage = advantage + advan + ". ";
        }
        laptopres.setAdvantage(advantage);

        List<WebElement> list = driver
                .findElements(By.xpath("//table[@class='st-pd-table']/tbody/tr"));
        for (WebElement element : list) {
            String techName = element.findElement(By.xpath("./td[1]")).getText();
            String techInfor = element.findElement(By.xpath("./td[2]")).getText();
            final String laptopDetail;
            final String[] laptopDetailSplit;
            laptopDetailSplit = techInfor.split(" ");
            laptopDetail = techInfor.replaceAll("[^0-9,\\.]", ",");
            String[] item = laptopDetail.split(",");
            for (int i = 0; i < item.length; i++) {
                try {
                    Double.parseDouble(item[i]);
                    System.out.println(item[i] + " " + i);
                } catch (NumberFormatException e) {
                }
            }
            System.out.println(techName + ' ' + techInfor);
            switch (techName) {
                case "CPU":
                    laptopres.setCpu(techInfor);
                    break;
                case "RAM":
                    laptopres.setRam(Long.parseLong(item[0]));
                    break;
                case "Ổ cứng":
                    laptopres.setStorageType(laptopDetailSplit[0]);
                    laptopres.setStorageCapacity(Long.parseLong(item[4]));
                    break;
                case "Màn hình":
                    laptopres.setScreen(techInfor);
                    break;
                case "Đồ họa":
                    laptopres.setGraphic(techInfor);
                    break;
                case "Trọng lượng":
                    laptopres.setWeight(Double.parseDouble(item[0]));
                    break;
                case "Kích thước":
                    laptopres.setLength(Double.parseDouble(item[0]));
                    laptopres.setWidth(Double.parseDouble(item[3]));
                    if (!item[5].equals("")) {
                        laptopres.setThick(Double.parseDouble(item[5]));
                    } else if (!item[6].equals("")) {
                        laptopres.setThick(Double.parseDouble(item[6]));
                    }
                    break;
                case "Hệ điều hành":
                    laptopres.setOs(techInfor);
                    break;
            }
        }
        driver.close();
        if (laptopFPTRepository.findByName(laptopres.getName()) != null) {
            laptop = laptopFPTRepository.findByName(laptopres.getName());
            laptop.setAdvantage(laptopres.getAdvantage());
            laptop.setThick(laptopres.getThick());
            laptop.setLength(laptopres.getLength());
            laptop.setWidth(laptopres.getWidth());
            laptop.setArea(laptopres.getArea());
            laptop.setCpu(laptopres.getCpu());
            laptop.setGraphic(laptopres.getGraphic());
            laptop.setName(laptopres.getName());
            laptop.setOs(laptopres.getOs());
            laptop.setPrice(laptopres.getPrice());
            laptop.setRam(laptopres.getRam());
            laptop.setScreen(laptopres.getScreen());
            laptop.setStorageCapacity(laptopres.getStorageCapacity());
            laptop.setStorageType(laptopres.getStorageType());
            laptop.setWeight(laptopres.getWeight());
            updatedLaptop = laptopFPTRepository.save(laptop);
        } else {
            laptop.setAdvantage(laptopres.getAdvantage());
            laptop.setThick(laptopres.getThick());
            laptop.setLength(laptopres.getLength());
            laptop.setWidth(laptopres.getWidth());
            laptop.setArea(laptopres.getArea());
            laptop.setCpu(laptopres.getCpu());
            laptop.setGraphic(laptopres.getGraphic());
            laptop.setName(laptopres.getName());
            laptop.setOs(laptopres.getOs());
            laptop.setPrice(laptopres.getPrice());
            laptop.setRam(laptopres.getRam());
            laptop.setScreen(laptopres.getScreen());
            laptop.setStorageCapacity(laptopres.getStorageCapacity());
            laptop.setStorageType(laptopres.getStorageType());
            laptop.setWeight(laptopres.getWeight());
            updatedLaptop = laptopFPTRepository.save(laptop);
        }

        return updatedLaptop;
    }

    @Override
    public LaptopAmz crawlLaptopAmz(UrlRequest url) {
        LaptopAmz laptop = new LaptopAmz();
        LaptopResponse laptopres = new LaptopResponse();
        LaptopAmz updatedLaptop = new LaptopAmz();
        System.setProperty("webdriver.chrome.driver", AppConstants.CHROME_DRIVER);
        WebDriver driver = new ChromeDriver();
        driver.get(url.getUrl());
        String laptopName = driver.findElement(By.xpath("//span[@id='productTitle']"))
                .getText();
        laptopres.setName(laptopName);
        String price = driver
                .findElement(By.xpath("//span[@class='a-price a-text-price a-size-medium apexPriceToPay']")).getText();
        String priceReplace = price.replaceAll("[^0-9,\\.]", " ");
        priceReplace = priceReplace.replace(",", "");
        priceReplace = priceReplace.replace(".", " ");
        String[] priceSplit = priceReplace.split(" ");
        System.out.println("price: " + priceReplace);
        laptopres.setPrice(Long.parseLong(priceSplit[1]) * 23000);
        if (price.isEmpty()) {
            String price2 = driver
                    .findElement(By.xpath("//span[@class='a-price a-text-price a-size-base']")).getText();
            String priceReplace2 = price2.replaceAll("[^0-9,\\.]", " ");
            priceReplace2 = priceReplace2.replace(",", "");
            priceReplace2 = priceReplace2.replace(".", " ");
            String[] priceSplit2 = priceReplace2.split(" ");
            laptopres.setPrice(Long.parseLong(priceSplit2[1]) * 23000);
        }
        driver.findElement(By.xpath("//a[@class='a-declarative']")).click();
        List<WebElement> list = driver
                .findElements(By.xpath("//table[@class='a-normal a-spacing-micro']/tbody/tr"));
        for (WebElement element : list) {
            String techName = element.findElement(By.xpath("./td[@class='a-span3']")).getText();
            String techInfor = element.findElement(By.xpath("./td[@class='a-span9']")).getText();
            final String laptopDetail;
            final String[] laptopDetailSplit;
            laptopDetailSplit = techInfor.split(" ");
            laptopDetail = techInfor.replaceAll("[^0-9,\\.]", ",");
            String[] item = laptopDetail.split(",");
            for (int i = 0; i < item.length; i++) {
                try {
                    Double.parseDouble(item[i]);
                    System.out.println(item[i] + " " + i);
                } catch (NumberFormatException e) {
                }
            }
            System.out.println(techName + ' ' + techInfor);
            switch (techName) {
                case "Screen Size":
                    laptopres.setScreen(techInfor);
                    break;
                case "Hard Disk Size":
                    laptopres.setStorageCapacity(Long.parseLong(item[0]));
                    break;
                case "Ram Memory Installed Size":
                    laptopres.setRam(Long.parseLong(item[0]));
                    break;
                case "Operating System":
                    laptopres.setOs(techInfor);
                    break;
                case "Graphics Coprocessor":
                    laptopres.setGraphic(techInfor);
                    break;
                case "CPU Model":
                    laptopres.setCpu(techInfor);
                    break;
            }
        }
        if (laptopAmzRepository.findByName(laptopres.getName()) != null) {
            laptop = laptopAmzRepository.findByName(laptopres.getName());
            laptop.setAdvantage(laptopres.getAdvantage());
            laptop.setThick(laptopres.getThick());
            laptop.setLength(laptopres.getLength());
            laptop.setWidth(laptopres.getWidth());
            laptop.setCpu(laptopres.getCpu());
            laptop.setGraphic(laptopres.getGraphic());
            laptop.setName(laptopres.getName());
            laptop.setOs(laptopres.getOs());
            laptop.setPrice(laptopres.getPrice());
            laptop.setRam(laptopres.getRam());
            laptop.setScreen(laptopres.getScreen());
            laptop.setStorageCapacity(laptopres.getStorageCapacity());
            laptop.setStorageType(laptopres.getStorageType());
            laptop.setWeight(laptopres.getWeight());
            updatedLaptop = laptopAmzRepository.save(laptop);
        } else {
            laptop.setAdvantage(laptopres.getAdvantage());
            laptop.setThick(laptopres.getThick());
            laptop.setLength(laptopres.getLength());
            laptop.setWidth(laptopres.getWidth());
            laptop.setCpu(laptopres.getCpu());
            laptop.setGraphic(laptopres.getGraphic());
            laptop.setName(laptopres.getName());
            laptop.setOs(laptopres.getOs());
            laptop.setPrice(laptopres.getPrice());
            laptop.setRam(laptopres.getRam());
            laptop.setScreen(laptopres.getScreen());
            laptop.setStorageCapacity(laptopres.getStorageCapacity());
            laptop.setStorageType(laptopres.getStorageType());
            laptop.setWeight(laptopres.getWeight());
            updatedLaptop = laptopAmzRepository.save(laptop);
        }
        driver.close();
        return updatedLaptop;
    }

    @Override
    public LaptopAmz crawlLaptopAmzBySearchBar() {
        LaptopAmz laptop = new LaptopAmz();
        LaptopResponse laptopres = new LaptopResponse();
        LaptopAmz updatedLaptop = new LaptopAmz();
        String laptopName = "MacBook Pro 14";
        System.setProperty("webdriver.chrome.driver", AppConstants.CHROME_DRIVER);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(laptopName);
        driver.findElement(By.id("nav-search-submit-button")).click();
        List<WebElement> laptopList = driver.findElements(By.xpath(
                "//div[@class='s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 sg-col s-widget-spacing-small sg-col-12-of-16']"));
        System.out.println(laptopList.size());
        for (WebElement element : laptopList) {
            if (element.findElement(By.xpath("./span[@class='a-price-whole']")).isSelected()) {
                System.out.println(element.findElement(By.xpath("./span[@class='a-price-whole']")).getText());
            }
        }
        driver.close();
        return laptop;
    }

    @Override
    public void crawlLaptopList() {
        List<LaptopFPT> laptopFPTList = new ArrayList<LaptopFPT>();
        // List<String> url = new ArrayList<String>();
        String[] urlListFPT = { "https://fptshop.com.vn/may-tinh-xach-tay/hp-15s-du1110tu-i3-10110u",
                "https://fptshop.com.vn/may-tinh-xach-tay/macbook-pro-16-2021-m1-pro?dung-luong=512gb",
                "https://fptshop.com.vn/may-tinh-xach-tay/macbook-pro-14-2021-m1-pro?dung-luong=512gb",
                "https://fptshop.com.vn/may-tinh-xach-tay/acer-aspire-gaming-a715-42g-r4xx-r5-5500u",
                "https://fptshop.com.vn/may-tinh-xach-tay/lenovo-ideapad-slim-3-15itl6-i5-1135g7?he-dieu-hanh=win-11",
                "https://fptshop.com.vn/may-tinh-xach-tay/hp-pavilion-15-eg0540tu-i5-1135g7?he-dieu-hanh=win-11",
        };
        for (String url : urlListFPT) {
            UrlRequest req = new UrlRequest();
            req.setUrl(url);
            laptopFPTList.add(crawlLaptopFPT(req));
        }

        List<LaptopAmz> laptopAmzList = new ArrayList<LaptopAmz>();
        List<String> uList = laptopAmzRepository.findAllLaptopAmzUrl();
        String[] urlListAmz = {
                "https://www.amazon.com/Samsung-Electronics-Platform-Processor-Long-Lasting/dp/B09D8SGXB6/ref=sr_1_3?crid=2IFA2JQI69CT&keywords=Samsung%2BGalaxy%2BBook%2BPro%2BWindows%2B11%2BIntel%2BEvo%2BPlatform%2BLaptop%2BComputer%2B13.3%22%2BAMOLED%2BScreen%2B11th%2BGen%2BIntel%2BCore%2Bi5%2BProcessor%2B8GB%2BMemory%2B256GB%2BSSD%2BLong-Lasting%2BBattery%2C%2BMystic%2BSilver&qid=1653188478&sprefix=samsung%2Bgalaxy%2Bbook%2Bpro%2Bwindows%2B11%2Bintel%2Bevo%2Bplatform%2Blaptop%2Bcomputer%2B13.3%2Bamoled%2Bscreen%2B11th%2Bgen%2Bintel%2Bcore%2Bi5%2Bprocessor%2B8gb%2Bmemory%2B256gb%2Bssd%2Blong-lasting%2Bbattery%2C%2Bmystic%2Bsilver%2Caps%2C436&sr=8-3&th=1",
                "https://www.amazon.com/Dell-Latitude-5520-11-1135-8GB/dp/B08TLPNZ47/ref=sr_1_4?crid=3Q5LD5OT7SJC&keywords=Dell+Latitude+5520&qid=1654508527&sprefix=dell+latitude+5520%2Caps%2C332&sr=8-4",
                "https://www.amazon.com/ASUS-Chromebook-Touchscreen-Magnesium-Alloy-CX9400CEA-DS762T/dp/B09F8Y2HJW/ref=sr_1_3?crid=2KBIMCYMZB810&keywords=ASUS+Chromebook+CX9&qid=1653193599&sprefix=asus+chromebook+cx9%2Caps%2C929&sr=8-3",
                "https://www.amazon.com/HP-Dual-Core-Graphics-Bluetooth-Windows/dp/B091PB9BZ4/ref=sr_1_1?crid=2A26KM4AFLMXF&keywords=2021+HP+15.6%27%27+HD+Laptop+PC+AMD+Dual-Core+Ryzen+3+3250U+4GB+DDR4+128GB+SSD+%2B+1TB+HDD+AMD+Radeon+Graphics+USB-C+HDMI+WiFi+AC+RJ45+Bluetooth+Webcam+HP+Fast+Charge+Windows+10+Home&qid=1653193797&sprefix=2021+hp+15.6%2Caps%2C377&sr=8-1",
                "https://www.amazon.com/ASUS-Display-GeForce-Keyboard-G533ZX-XS96/dp/B09RN27LHT/ref=sr_1_4?crid=2LAB7MP3ZI3JF&keywords=ASUS%2BROG%2BStrix%2BScar%2B15&qid=1653194009&sprefix=asus%2Brog%2Bstrix%2Bscar%2B15%2Caps%2C402&sr=8-4&th=1",
                "https://www.amazon.com/Lenovo-IdeaPad-Processor-Graphics-82KT00GVUS/dp/B09BG841VC/ref=sr_1_4?crid=1ICIY6Y9L1DEH&keywords=Lenovo+IdeaPad+3+Laptop%2C+14.0%22&qid=1653194071&sprefix=lenovo+ideapad+3+laptop%2C+14.0+%2Caps%2C1009&sr=8-4",
                "https://www.amazon.com/HP-i5-1135G7-Processor-Micro-Edge-15-eg0010nr/dp/B08KGZVHCV/ref=sr_1_4?crid=3MG658JPWBB4X&keywords=HP+Pavilion+15+Laptop%2C+11th+Gen&qid=1653194120&sprefix=hp+pavilion+15+laptop%2C+11th+gen%2Caps%2C309&sr=8-4",
                "https://www.amazon.com/ASUS-GeForce-i7-10875H-Windows-G532LWS-DS76/dp/B0876M6CG9/ref=sr_1_5?crid=NDX2B2TISAAG&keywords=ASUS%2BROG%2BStrix%2BScar%2B15&qid=1653194236&sprefix=asus%2Brog%2Bstrix%2Bscar%2B15%2Caps%2C484&sr=8-5&th=1",
                "https://www.amazon.com/Dell-XPS-17-9700-i7-10875H/dp/B094PJN4SG/ref=sr_1_3?crid=3EQG0RMCGZSH&keywords=Dell+XPS+17+9700&qid=1654508857&sprefix=dell+xps+17+9700%2Caps%2C978&sr=8-3" };
        for (String url : urlListAmz) {
            UrlRequest req = new UrlRequest();
            req.setUrl(url);
            laptopAmzList.add(crawlLaptopAmz(req));
        }

        List<LaptopDMX> laptopListDMX = new ArrayList<LaptopDMX>();
        String[] urlListDMX = { "https://www.dienmayxanh.com/laptop/apple-pro-16-m1-pro-2021-10-core-cpu?src=osp",
                "https://www.dienmayxanh.com/laptop/macbook-pro-14-m1-max-2021-10-core-cpu?src=osp",
                "https://www.dienmayxanh.com/laptop/acer-aspire-7-gaming-a715-42g-r4xx-r5-nhqaysv008?src=osp",
                "https://www.dienmayxanh.com/laptop/dell-inspiron-15-3511-i5-70270650?src=osp",
                "https://www.dienmayxanh.com/laptop/hp-pavilion-15-eg0504tu-i7-1165g7-46m00pa?src=osp",
                "https://www.dienmayxanh.com/laptop/dell-vostro-5510-i5-70270646",
        };
        for (String url : urlListDMX) {
            UrlRequest req = new UrlRequest();
            req.setUrl(url);
            laptopListDMX.add(crawlLaptopDMX(req));
        }

    }

}
