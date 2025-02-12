import java.text.SimpleDateFormat
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.BiPredicate 
import java.util.function.Predicate
import java.util.stream.Collectors 
import java.util.stream.IntStream 

import groovy.json.JsonSlurper

class JsonHelper
{
     static String convertEpoch_to_date(long timeInMillis)
    {
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy") 
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS") 
        Calendar calendar = new GregorianCalendar() 
        format.setTimeZone(calendar.getTimeZone()) 
        return format.format(timeInMillis) 
    }
     static String convertEpoch_to_date(long timeInMillis, String formatIn)
    {
        SimpleDateFormat format = new SimpleDateFormat(formatIn) 
        Calendar calendar = new GregorianCalendar() 
        format.setTimeZone(calendar.getTimeZone()) 
        return format.format(timeInMillis) 
    }
     static List<?> lookUpValueFieldFromJson_fromList( List<Map<?,?>> jsonArray, String sField )
    {
        String sVal 
        List<?> sFilter = null 

        try {

            sFilter = jsonArray.stream().filter{ map -> map.containsKey(sField) }.map{ map -> map.get(sField) }.collect( Collectors.toList() ) 

        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage()) 
            e.printStackTrace() 
        }
        if( sFilter != null && !sFilter.isEmpty() )
            return sFilter 
        else
            return null 
    }
     static String lookUpValueFieldFromJson( String sJSON, String sField )
    {
        String sVal = null 
        String sResAux =  sJSON 

        JsonSlurper jsonParser = new JsonSlurper() 

        try {
            Map jsonMap = ( Map ) jsonParser.parse( sResAux.getBytes("utf-8") ) 

            sVal = (String) jsonMap.get(sField) 

        } catch (UnsupportedEncodingException e) {
            System.out.print("UnsupportedEncodingException in lookUpValueField Method: " + e.getMessage()) 
            e.printStackTrace() 
        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage()) 
            e.printStackTrace() 
        }
        if( sVal != null )
            return sVal 
        else
            return "" 
    }

    static Object[] lookUpValuesFieldFromJson(String sJSON, String sField )
    {
        String sVal 
        List<Object> sFilter = null 
        Object[] retval = {} 
        String sResAux =  sJSON 

        JsonSlurper jsonParser = new JsonSlurper() 

        try {
            List<Map> jsonMap = (List<Map>) jsonParser.parse( sResAux.getBytes("utf-8") ) 

            sFilter = jsonMap.stream().filter {   map -> map.containsKey(sField)}
                    .map{map -> map.get(sField)}.collect(Collectors.toList()) 

            retval = sFilter.toArray() 

        } catch (UnsupportedEncodingException e) {
            System.out.print("UnsupportedEncodingException in lookUpValueField Method: " + e.getMessage()) 
            e.printStackTrace() 
        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage()) 
            e.printStackTrace() 
        }
        return retval 
    }
     static List<?> lookUpValuesFieldFromGenericJson( String sJSON, String sField ) throws Exception
    {
        String sVal 
        ArrayList<?> sFilter = null 
        List<Map<?,?>> listAux = new ArrayList<>() 
        String sResAux =  sJSON 
        JsonSlurper jsonParser = new JsonSlurper() 

        if( jsonParser.parse( sResAux.getBytes("utf-8") ) instanceof ArrayList<?> )
        {
            listAux.addAll( parseJsonToArrayList( sJSON ) ) 
        }
        if( jsonParser.parse( sResAux.getBytes("utf-8") ) instanceof Map<?,?> )
        {
            listAux.add(parseJsonToMap( sJSON )) 
        }
        sFilter = (ArrayList<?>) listAux.stream().filter {  map -> map.containsKey(sField)}
                .map{map -> map.get(sField)}
                .collect(Collectors.toList()) 

        return sFilter 
    }
     static Map<?, ?> parseJsonToMap( String sJSON ) throws Exception
    {
        String sVal 
        List<Object> sFilter = null 
        Map<String, ?> retval = new HashMap<>() 
        String sResAux =  sJSON 
        JsonSlurper jsonParser = new JsonSlurper() 
        retval = ( Map ) jsonParser.parse( sResAux.getBytes("utf-8") ) 
        return retval 
    }
     static List<Map<?,?>> parseJsonToArrayList( String sJSON )
    {
        List<Map<?,?>> retval = new ArrayList<>() 
        JsonSlurper jsonParser = new JsonSlurper() 

        try {
            retval = (List<Map<?, ?>>) jsonParser.parse( sJSON.getBytes("utf-8") ) 
        } catch (UnsupportedEncodingException e) {
            System.out.print("UnsupportedEncodingException in lookUpValueField Method: " + e.getMessage()) 
            e.printStackTrace() 
        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage()) 
            e.printStackTrace() 
        }
        if( retval != null )
            return retval 
        else
            return null 
    }
     static List<Map<?, ?>> convertJsonNodeToArray( Object sJsonObj ) throws Exception
    {
        String sVal 
        List<Map<?,?>> listAux = new ArrayList<Map<?,?>>() 
        if( sJsonObj instanceof ArrayList<?> )
        {
            listAux.addAll( (Collection<? extends Map<?, ?>>) sJsonObj ) 
        }
        if( sJsonObj instanceof Map<?,?> )
        {
            listAux.add( (Map<?, ?>) sJsonObj ) 
        }
        return listAux 
    }
     static List<String> removeAdjacentDuplicates( List<String> listIn)
    {
        List<String> acc = IntStream
                .range(0, listIn.size())
                .filter {  i -> ((i < listIn.size() - 1 && !listIn.get(i).equals(listIn
                        .get(i + 1))) || i == listIn.size() - 1) }
                .mapToObj{i -> listIn.get(i)}
                .collect(Collectors.toList()) 

        return acc 
    }


     static List<?> filterJson( Object jJSON, String sKey, String sVal ) throws Exception
    {
        ArrayList<?> sFilter = null 
        List<Map<?,?>> listAux = new ArrayList<>() 
        JsonSlurper jsonParser = new JsonSlurper() 

        if( jJSON instanceof ArrayList<?> )
        {
            listAux.addAll(  (Collection<? extends Map<?, ?>>) jJSON  ) 
        }
        if( jJSON instanceof Map<?,?> )
        {
            listAux.add( (Map<?, ?>) jJSON ) 
        }
        sFilter = (ArrayList) listAux.stream()
                .filter {  Map item ->   return ( (String) item.get(sKey) ).contains( ( sVal ) )   }
                .collect(Collectors.toList()) 

        return sFilter 
    }

     static List<?> advancedFilterJson_v2( Object jJSON, String sFilterIn) throws Exception
    {


        BiPredicate<Map<String, ?>,String> pFilter =  { map, string -> String[] sCond = string.split(" ") 
            String sKey = sCond[0] 
            String sVal = sCond[2] 
            boolean bRet = false 
            if ( sCond[1].equalsIgnoreCase("gt") )
            {
                bRet = ( Double.parseDouble((String) map.get(sKey)) > Double.parseDouble(sVal) ) 
            }
            else if (sCond[1].equalsIgnoreCase("lt"))
            {
                bRet = ( Double.parseDouble((String) map.get(sKey)) < Double.parseDouble(sVal) ) 
            }
            return bRet 
        } 

        Predicate<Map<String, ?>> pGlobalFilter = { map ->  AtomicBoolean bRet = new AtomicBoolean() 
            boolean bAux = true 
            if(!sFilterIn.contains("&"))
                bRet.set( pFilter.test(map, sFilterIn )) 
            else
            {
                String[] sCond = sFilterIn.split("&") 
                for(String sIn : sCond)
                {
                    bAux = bAux && pFilter.test(map, sIn) 
                }
                bRet.set(bAux) 
            }
            return bRet.get() 

        } 

        ArrayList<?> sFilter = null 
        List<Map<?,?>> listAux = new ArrayList<>() 

        if( jJSON instanceof ArrayList<?> )
        {
            listAux.addAll(  (Collection<? extends Map<?, ?>>) jJSON  ) 
        }
        if( jJSON instanceof Map<?,?> )
        {
            listAux.add( (Map<?, ?>) jJSON ) 
        }
        sFilter = (ArrayList) listAux.stream()
                .filter{ Map item -> pGlobalFilter.test(item) }
                .collect(Collectors.toList()) 

        return sFilter 
    }
}